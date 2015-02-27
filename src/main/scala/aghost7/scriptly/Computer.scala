
import java.io.File

/** Interactive utilities object
 */
object Computer {

	def toClipboard(s: String) {
		import java.awt.Toolkit
		import java.awt.datatransfer.StringSelection
		val select = new StringSelection(s)
		Toolkit
			.getDefaultToolkit
			.getSystemClipboard
			.setContents(select, select)
	}
	
	def askFile: Option[File] = {
		import javax.swing.JFileChooser
		val fc = new JFileChooser
		val clickResult = fc.showOpenDialog(null)
		if(clickResult == JFileChooser.APPROVE_OPTION){
			Some(fc.getSelectedFile)
		} else {
			None
		}
	
	}
}