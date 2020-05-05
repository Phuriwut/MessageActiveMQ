import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import org.apache.log4j.BasicConfigurator;

public class main_ice {
    public static void main(String args[]){
        BasicConfigurator.configure();

        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(3700);

        SocketIOServer server = new SocketIOServer(config);

        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                HandshakeData hcd = client.getHandshakeData();
                System.out.println("connected : " + client.getSessionId().toString());
                System.out.println("namespace :" + client.getNamespace().getName());
                System.out.println("url : " + hcd.getUrl());
                System.out.println("header : " + hcd.getHttpHeaders().toString());
            }
        });

        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                System.out.println("disconnected " + client.getSessionId().toString());
            }
        });

        server.start();
    }

}