package loustic

import groovy.util.logging.Log4j

@Log4j
class SocketManager{

    static private final int openPort = 12001 //to be changed later.

    static def server = null
    static boolean started = false

    static Map openSockets = [:]
    static int connectionId = 1 //to be read from dB

    static void handleMsg(String msg, int id, Socket socket) {
            println(msg)
            listen(id, socket)
    }

    static void sendToSocket(String msg , Socket socket){
        if(!msg.endsWith('\n')) msg = "$msg\n"
        socket << msg
    }

    static void broadcastMessage(String msg){
        if(!msg.endsWith('\n')) msg = "$msg\n"
        openSockets.each{ id , socket ->
            socket << msg
        }
    }

    static void listen(int id, Socket socket) {
        String msg = socket.inputStream.newReader().readLine()
        handleMsg(msg, id, socket)
    }

    static void run() {
        //init Server Socket
        log.debug "Starting SocketManager"
        try{
        server = new ServerSocket(openPort)
        started = true

        Thread.start {
            while (started) {
                log.info "SocketServer started on port $openPort"
                server.accept { socket ->
                    connectionId++
                    log.debug "New connection established to socket: CID=$connectionId" 
                    openSockets.put(connectionId, socket)
                    listen(connectionId, socket)
                    log.debug "Connection closed: CID=$connectionId"
                    openSockets.remove(connectionId)
                    }
                }
            }
        }
        catch(Exception e){
            log.error e.message
            log.error e.getStackTrace()
        }
    }
}