package loustic

import groovy.sql.Sql
import groovy.util.logging.Log4j
import loustic.Profile
import loustic.DbUtils

@Log4j
class ProfileManager {
	private static ProfileManager instance

	private static final String LS_USER_TABLE = 'LS_USER'
	
	static ProfileManager getInstance() { 
		if (!instance) instance = new ProfileManager()
		return instance
	}

	private ProfileManager(){
			def metadata = DbUtils.getInstance().DbHandler().connection.getMetaData()
			def tables = metadata.getTables(null, null, LS_USER_TABLE, null)

			if (!tables.next()) {
    			// table does not exist
    			log.debug "Creating table ${LS_USER_TABLE}"
    			String createUserTable = "CREATE TABLE ${LS_USER_TABLE} (ID INTEGER PRIMARY KEY , USER TEXT , PWD TEXT)"
    			DbUtils.getInstance().DbHandler().execute(createUserTable)
			} else {
    			log.debug("table ${LS_USER_TABLE} already exists")
			}
	}

	LSResult authenticate(Profile profile){

		String query = $/ SELECT USER,PWD FROM LS_USER WHERE USER = '${profile.email}' /$
		String msg = "User not found"
		boolean authenticated = false

		DbUtils.getInstance().DbHandler().rows(query).each{ row ->
			if(row.PWD == profile.password){
				authenticated = true
				msg = 'authenticated'
			}
			else{
				msg = "incorrect password"
			}
		}

		return new LSResult(ok:authenticated,msg:msg,code:200)
	}

}
