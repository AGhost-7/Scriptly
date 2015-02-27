package aghost7.scriptly
import java.sql.{Connection, DriverManager}
import scala.util.Try
import java.net.{URISyntaxException, URI}

object SqlConnection {
	
	private val driverNames = 
		Map("postgresql" -> "org.postgresql.Driver",
				"mysql" -> "com.mysql.jdbc.Driver")
	
	private def findDriver(uri: String): String = {
		val reg = """^(jdbc:)(.+)(:\/\/.+)""".r
		val mOpt = reg.findFirstMatchIn(uri)
		
		if(!mOpt.isDefined){
			throw new URISyntaxException(uri, "JDBC uri not valid. Please enter " +
					"a driver name instead.");
		}
		
		val dbName = mOpt.get.group(2)
		val driverNameOpt = driverNames.get(dbName)
		
		if(!driverNameOpt.isDefined){
			throw new RuntimeException(s"Database $dbName is not automatically handled. " + 
					"You must manually enter the driver name.")
		}
		
		driverNameOpt.get
	}
	
	def defaultUri(baseUri: String): String = 
		if(!baseUri.contains("useOldAliasMetadataBehavior"))
			if(baseUri.contains("?"))
				baseUri + "&useOldAliasMetadataBehavior=true"
			else
				baseUri + "?useOldAliasMetadataBehavior=true"
		else
			baseUri
		
	
	
	//useOldAliasMetadataBehavior=true
	def apply(uri: String, 
			driver: Option[String] = None)
			(func: Connection => Unit) {
		
		val driverStr = driver.getOrElse { findDriver(uri) }
		Class.forName(driverStr)
		
		val uriDefault = defaultUri(uri)
		
		val con = DriverManager.getConnection(uriDefault)
		try { func(con) } 
		finally { con.close() }
	}
}