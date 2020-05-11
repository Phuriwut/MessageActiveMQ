package eventhandler;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DisconnectListener;
import session.SessionStore;
import session.SessionStoreInstance;

public class DisconnectEvent implements DisconnectListener {
    SessionStoreInstance session = SessionStore.getInstance();
    @Override
    public void onDisconnect(SocketIOClient client) {
        System.out.println("disconnected " + client.getSessionId().toString());
        session.removeSessionData(client.getSessionId().toString());
    }
}
