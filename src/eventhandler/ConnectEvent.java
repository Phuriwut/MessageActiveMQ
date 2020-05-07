package eventhandler;

import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;

public class ConnectEvent implements ConnectListener {

    @Override
    public void onConnect(SocketIOClient client) {
        HandshakeData hcd = client.getHandshakeData();
        System.out.println("connected : " + client.getSessionId().toString());
        System.out.println("namespace :" + client.getNamespace().getName());
        System.out.println("url : " + hcd.getUrl());
        System.out.println("header : " + hcd.getHttpHeaders().toString());
    }
}
