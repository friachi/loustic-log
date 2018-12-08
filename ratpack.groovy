@Grapes([
        @Grab('io.ratpack:ratpack-groovy:1.5.0'),
        @Grab('org.slf4j:slf4j-simple:1.7.25'),
        @Grab(group='org.xerial', module='sqlite-jdbc', version='3.23.1'),
        @Grab(group='log4j', module='log4j', version='1.2.17'),
        @Grab(group='com.google.code.gson', module='gson', version='2.8.5')
])

import static ratpack.groovy.Groovy.ratpack
import static ratpack.util.Types.listOf;
import static ratpack.jackson.Jackson.jsonNode;
import static ratpack.jackson.Jackson.fromJson;
import groovy.util.logging.Log4j
import ratpack.jackson.Jackson;
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.Gson
import groovy.json.JsonOutput
import loustic.Profile
import loustic.LSResult
import loustic.ProfileManager
import loustic.DbUtils
import loustic.Role
import loustic.LousticSession
import loustic.SocketManager
import loustic.SessionManager


@Log4j
class Main {

static void run() {
    log.debug "Starting ratpack REST server"
    ratpack {
    handlers {
        get {
            try{
            render "Hello World!"
            log.info('get method accessed')
            Profile Georges = Profile.georgesPlaceholderTest()
            println Georges.toString()
            log.info Profile.toJson(Georges)
            LousticSession ls = LousticSession.georgesPlaceholderTest()
            println ls.toString()
            log.info LousticSession.toJson(ls)
        }
        catch(Exception e){
            log.error e.message
            log.error e.getStackTrace()
        }
            
        }

        post('ls/auth'){
        
            def postBody = parse jsonNode()
            postBody.then { json ->
            println "Body is ${json.toString()}"
            Profile profile = Profile.fromJson(json.toString())
            log.info('ls/auth')
            DbUtils.getInstance()
            LSResult res = ProfileManager.getInstance().authenticate(profile)
            log.info("Res: ${res.toString()}")
            response.status(res.code).send(res.msg)
        }
            
        }

        put('ls/session'){

            def postBody = parse jsonNode()
            LSResult res
            postBody.then { json ->
            log.debug "Body is ${json.toString()}"
            LousticSession ls = LousticSession.fromJson(json.toString())
            log.info('put ls/session')
            ls = SessionManager.getInstance().createSession(ls)
            if(ls){
                res = new LSResult(ok:true,msg:'session created',code:201)
            }
            else{
                res = new LSResult(ok:false,msg:'failed to create session',code:500)
            }
            log.info("Res: ${res.toString()}")
            String lsJson = LousticSession.toJson(ls)

            response.status(res.code).send(lsJson)
        }
        }

        delete('ls/session/delete'){
            def postBody = parse jsonNode()
            LSResult res
            postBody.then { json ->
            log.debug "Body is ${json.toString()}"
            LousticSession ls = LousticSession.fromJson(json.toString())
            log.info('delete ls/session')
            boolean deleted = SessionManager.getInstance().deleteSession(ls)
            if(deleted){
                res = new LSResult(ok:deleted,msg:'session deleted',code:200)
            }
            else{
                res = new LSResult(ok:deleted,msg:'failed to delete session',code:500)
            }
            log.info("Res: ${res.toString()}")
            
            response.status(res.code).send(res.msg)
        }
        }

        post('ls/session'){
            def postBody = parse jsonNode()
            LSResult res
            postBody.then { json ->
            log.debug "Body is ${json.toString()}"
            LousticSession ls = LousticSession.fromJson(json.toString())
            log.info('post ls/session')
            ls = SessionManager.getInstance().updateSession(ls)
            if(ls){
                res = new LSResult(ok:true,msg:'session updated',code:200)
            }
            else{
                res = new LSResult(ok:false,msg:'failed to update session',code:500)
            }
            log.info("Res: ${res.toString()}")
            String lsJson = LousticSession.toJson(ls)

            response.status(res.code).send(lsJson)
        }
        }

        post('ls/session/all'){
            
            log.info('ls/session/all')
            ArrayList<LousticSession> allSessions = SessionManager.getInstance().getAllSessions()
            String jsonRes = JsonOutput.toJson(allSessions)
            log.info("Res: ${jsonRes}")
            response.status(200).send(jsonRes)
        }
    }
    }
}

}

//Start Socket
SocketManager.run()

//Start REST ratpack
Main.run()