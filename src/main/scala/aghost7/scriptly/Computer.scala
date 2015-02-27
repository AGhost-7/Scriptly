import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

object Computer {
	def toClipboard(s: String) {
		val select = new StringSelection(s)
		Toolkit
			.getDefaultToolkit
			.getSystemClipboard
			.setContents(select, select)
	}
}