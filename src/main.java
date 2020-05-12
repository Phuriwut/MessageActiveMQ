import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.store.MemoryStoreFactory;

import javax.jms.*;

// activemq

import dataextracter.LoginExtracter;
import eventhandler.ConnectEvent;
import eventhandler.DisconnectEvent;
import dataextracter.RegisterExtracter;
import eventhandler.LoginListener;
import eventhandler.RegisterListener;

public class main {
    public static void main(String args[])  throws JMSException{

        Configuration config = new Configuration();
        config.setHostname("0.0.0.0");
        config.setPort(3700);
        MemoryStoreFactory msf = new MemoryStoreFactory();
        config.setStoreFactory(msf);
        //config.setStoreFactory(); how to use this

        SocketIOServer server = new SocketIOServer(config);

        server.addConnectListener(new ConnectEvent());
        server.addDisconnectListener(new DisconnectEvent());
        server.addEventListener("REGISTER", RegisterExtracter.class, new RegisterListener());
        server.addEventListener("LOGIN", LoginExtracter.class, new LoginListener());

        server.start();
        Thread th = new Thread(new UserSender());
        th.start();
    }
}
