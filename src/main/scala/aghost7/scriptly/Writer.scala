package aghost7.scriptly
import java.io._
import scala.util._

class Writer(val printWriter: PrintWriter) {
	def apply(str: String) = printWriter.println(str)
	def apply(any: Any) = printWriter.println(any)
	def print(str: String) = printWriter.print(str)
	def print(any: Any) = printWriter.print(any)
	def apply(ex: Throwable) = ex.printStackTrace(printWriter)
}

object Writer {
	
	def apply(path: String, autoflush: Boolean = true, append: Boolean = true)(func: Writer => Unit): Unit = Try {
		val file = new java.io.File(path)
		val parent = file.getParentFile()
		
		if(parent != null) file.getParentFile().mkdirs()
		if(!file.exists())	file.createNewFile()
		
		new PrintWriter(new BufferedWriter(new FileWriter(file, append)), autoflush)
	} match {
		case Success(pw) => 
			try { func(new Writer(pw)) } 
			finally { pw.close() }
		case Failure(e) => e.printStackTrace()
	}
		
}