package eventhandler;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DisconnectListener;

public class DisconnectEvent implements DisconnectListener {
    @Override
    public void onDisconnect(SocketIOClient client) {
        System.out.println("disconnected " + client.getSessionId().toString());
    }
}
