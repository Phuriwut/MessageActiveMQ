import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.store.MemoryStoreFactory;

import javax.jms.*;

// activemq

import dataextracter.Extracter;
import dataextracter.LoginExtracter;
import eventhandler.*;
import dataextracter.RegisterExtracter;

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
        server.addEventListener("LOGOUT", Extracter.class, new LogoutListen());

        server.start();
        Thread th = new Thread(new UserSender());
        th.start();
    }
}
