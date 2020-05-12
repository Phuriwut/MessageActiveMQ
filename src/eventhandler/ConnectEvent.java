package eventhandler;

import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import session.SessionData;
import session.SessionStore;
import session.SessionStoreInstance;

public class ConnectEvent implements ConnectListener {
    SessionStoreInstance session = SessionStore.getInstance();

    @Override
    public void onConnect(SocketIOClient client) {
        System.out.println("connected : " + client.getSessionId().toString());
        SessionData userSession= this.session.getSessionData(client.getSessionId().toString());
        System.out.println(userSession);
        if(userSession != null){
            userSession.setClient(client);
            System.out.println(userSession.isLogin());
            if(userSession.isLogin()) client.sendEvent("RECEIVE_PROFILE", userSession.toString());
        }else{
            this.session.addSessionData(client.getSessionId().toString(),new SessionData(client));
        }
    }
}
