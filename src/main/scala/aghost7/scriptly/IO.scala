package aghost7.scriptly

import java.io.File

object IO {
	lazy val jarPath = getClass.getProtectionDomain.getCodeSource.getLocation.toURI.getPath
	lazy val jarFile = new File(jarPath)
	lazy val outerDirectory = jarFile.getParentFile
	
	def outerFile(relativePath: String) : File =
		new File(outerDirectory.getAbsolutePath + "/" + relativePath)
}