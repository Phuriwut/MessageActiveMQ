import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.store.MemoryStoreFactory;
import javax.jms.*;
import constance.events.ServerEvents;
import dataextracter.Extracter;
import dataextracter.LoginExtracter;
import eventhandler.*;
import dataextracter.RegisterExtracter;
import session.SessionData;
import session.SessionStore;
import session.SessionStoreInstance;
import user.sender.data.NotificateAlert;
import user.sender.data.ScheduleLoop;

import java.util.Timer;
import java.util.TimerTask;

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

        server.addEventListener(ServerEvents.REGISTER.getString(), RegisterExtracter.class, new RegisterListener());
        server.addEventListener(ServerEvents.LOGIN.getString(), LoginExtracter.class, new LoginListener());
        server.addEventListener(ServerEvents.LOGOUT.getString(), Extracter.class, new LogoutListen());

        server.start();

        Timer timer = new Timer ();
        TimerTask sometask = new ScheduleLoop();
        timer.schedule (sometask, 0l, 1000*60*60);

        Thread th = new Thread(new UserSender());
        th.start();

        NotificateAlert nta = new NotificateAlert(server);
        nta.notificateStart();


    }
}
