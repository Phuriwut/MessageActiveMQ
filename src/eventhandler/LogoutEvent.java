package eventhandler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import dataextracter.Extracter;

public class LogoutEvent extends EventListener<Extracter>{

    @Override
    public void onData(SocketIOClient socketIOClient, Extracter registerExtracter, AckRequest ackRequest) throws Exception {
        this.sessionstore.removeSessionData(socketIOClient.getSessionId().toString());
    }
}
