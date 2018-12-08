package loustic

import groovy.sql.Sql
import groovy.util.logging.Log4j
import java.util.zip.*

@Log4j
class DbUtils {
	private static DbUtils instance = null
	private Sql _sql = null

	private static final String LS_CONF_TABLE = 'LS_CONF'
	
	static DbUtils getInstance() { 
		if (!instance) {
			instance = new DbUtils()
			log.debug('DbUtils.getInstance() invoked')
		}
		return instance
	}

	private DbUtils(){
			_sql = Sql.newInstance( 'jdbc:sqlite:lsdB.sqlite','org.sqlite.JDBC' )
			def metadata = _sql.connection.getMetaData()
			def tables = metadata.getTables(null, null, LS_CONF_TABLE, null)

			if (!tables.next()) {
    			// table does not exist
    			log.debug("creating table ${LS_CONF_TABLE}")
    			String createConfigTable = "CREATE TABLE ${LS_CONF_TABLE} (ID INTEGER PRIMARY KEY)"
    			_sql.execute(createConfigTable)
			} else {
    			// table exists do nothing
    			log.debug("table ${LS_CONF_TABLE} already exists")
			}
	}

	Sql DbHandler(){
		return _sql
	}

	static String zip(String str){
         if(!str) return ''
        def targetStream = new ByteArrayOutputStream()
        def zipStream = new GZIPOutputStream(targetStream)
        zipStream.write(str.getBytes('UTF-8'))
        zipStream.close()
        def zipped = targetStream.toByteArray()
        targetStream.close()
        return zipped.encodeBase64().toString()
    }

	static String unzip(String str){
          if(!str) return ''
        def inflaterStream = new GZIPInputStream(new ByteArrayInputStream(str.decodeBase64()))
        def uncompressedStr = inflaterStream.getText('UTF-8')
        return uncompressedStr.toString()
    }
}