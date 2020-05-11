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
        HandshakeData hcd = client.getHandshakeData();
        System.out.println("connected : " + client.getSessionId().toString());
        SessionData userSession = new SessionData(client);
        session.addSessionData(client.getSessionId().toString(), userSession);
    }
}
