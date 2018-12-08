package loustic

import loustic.LousticSession
import groovy.sql.Sql
import groovy.util.logging.Log4j
import loustic.DbUtils

@Log4j
class SessionManager {
	private static SessionManager instance

	private static final String LS_SESSION_TABLE = 'LS_SESSION'
	
	static SessionManager getInstance() { 
		if (!instance) instance = new SessionManager()
		return instance
	}
	
	private SessionManager(){
			def metadata = DbUtils.getInstance().DbHandler().connection.getMetaData()
			def tables = metadata.getTables(null, null, LS_SESSION_TABLE, null)

			if (!tables.next()) {
    			// table does not exist
    			log.debug "Creating table ${LS_SESSION_TABLE}"
    			String createTableQuery = "CREATE TABLE ${LS_SESSION_TABLE} (ID INTEGER PRIMARY KEY ,SESSION BLOB)"
    			DbUtils.getInstance().DbHandler().execute(createTableQuery)
			} else {
    			log.debug("table ${LS_SESSION_TABLE} already exists")
			}
	}

	ArrayList<LousticSession> getAllSessions(){
		ArrayList<LousticSession> result = []
		try{
		String selectAllQuery = "SELECT SESSION FROM ${LS_SESSION_TABLE}"
		DbUtils.getInstance().DbHandler().rows(selectAllQuery).each{ row ->
			String cSession = row.session
			String uSession = DbUtils.unzip(cSession)
			LousticSession ls = LousticSession.fromJson(uSession)
			result << ls
		}
		}
		catch(Exception e){
			log.error e.message
			log.error e.getStackTrace()
		}
		return result
	}

	synchronized LousticSession updateSession(LousticSession ls){
		try{
		int id = ls.id
		String uSession = LousticSession.toJson(ls)
		String cSession = DbUtils.zip(uSession)
		String updateSession = "UPDATE ${LS_SESSION_TABLE} SET SESSION = '${cSession}' WHERE ID = ${id}"

		DbUtils.getInstance().DbHandler().connection.setAutoCommit(false)
		DbUtils.getInstance().DbHandler()?.execute(updateSession)
        DbUtils.getInstance().DbHandler().commit()
        DbUtils.getInstance().DbHandler().connection.setAutoCommit(true)

        return ls
    	}
    	catch(Exception e){
    		log.error e.message
    		log.error e.getStackTrace()
    		return null
    	}
	}

	synchronized boolean deleteSession(LousticSession ls){
		log.info "Deleting session ${ls.id} from dB"
		try{
        String deleteQ= "DELETE FROM ${LS_SESSION_TABLE} WHERE ID = ${ls.id}"
        DbUtils.getInstance().DbHandler().connection.setAutoCommit(false)
        DbUtils.getInstance().DbHandler()?.execute(deleteQ)
        DbUtils.getInstance().DbHandler().commit()
        DbUtils.getInstance().DbHandler().connection.setAutoCommit(true)
        //DbUtils.getInstance().DbHandler().execute('VACUUM')
        return true
    	}
    	catch(Exception e){
    		e.error e.message
    		e.error e.getStackTrace()
    		return false
    	}
	}

	synchronized LousticSession createSession(LousticSession ls){
		try{
		int count = 0
		int id = -1
		String getRowCount = "SELECT COUNT(*) COUNT FROM ${LS_SESSION_TABLE}"
		DbUtils.getInstance().DbHandler().rows(getRowCount).each{ row ->
			count = row.COUNT
		}
		if(!count){
			id = 0
		}
		else{
			String maxId = "SELECT MAX(ID) MAX FROM ${LS_SESSION_TABLE}"
			DbUtils.getInstance().DbHandler().rows(maxId).each{ row ->
				id = row.MAX
			}
		}
		id++
		ls.id = id
		String uSession = LousticSession.toJson(ls)
		String cSession = DbUtils.zip(uSession)

		String insertSession = "INSERT INTO ${LS_SESSION_TABLE} (ID,SESSION) VALUES (${id},'${cSession}')"
		DbUtils.getInstance().DbHandler().connection.setAutoCommit(false)
		DbUtils.getInstance().DbHandler()?.execute(insertSession)
        DbUtils.getInstance().DbHandler().commit()
        DbUtils.getInstance().DbHandler().connection.setAutoCommit(true)

        return ls

    	}catch(Exception e){
    		log.error e.message
    		log.error e.getStackTrace()
    		return null
    	}
	}

}