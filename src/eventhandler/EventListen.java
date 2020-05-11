package eventhandler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import dataextracter.NotificationExtracter;
import dataextracter.RegisterExtracter;
import session.SessionData;
import session.SessionStore;
import session.SessionStoreInstance;
import Message.*;

import javax.jms.*;
import java.util.UUID;

public class EventListen implements DataListener<RegisterExtracter> {
    MessagerInstance messager;
    SessionStoreInstance sessionstore;

    public EventListen(){
        this.sessionstore = SessionStore.getInstance();
        try {
            this.messager = Messager.getInstance();
        }catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onData(SocketIOClient client, RegisterExtracter registerExtracter, AckRequest ackRequest) throws Exception {
        registerExtracter.setSessionID(client.getSessionId());
        System.out.println("Firstname: " + registerExtracter.getFirstname());
        System.out.println("SessionID : " + client.getSessionId());


        // We will send a small text message saying 'Hello World!!!'
        if(registerExtracter.getCareer() < 5 &&
                registerExtracter.getBank_id().length() == 12 &&
                registerExtracter.getBank_name() < 6 &&
                registerExtracter.getPassword().length() >= 6) {
            this.messager.send(registerExtracter.toString());

            System.out.println("Message Register::: '" + registerExtracter.toString()+ "'");
        }else {
            statusWarming(client);
        }
    }

    public void statusSuccess(SocketIOClient client){
        NotificationExtracter noti = new NotificationExtracter();
        noti.setStatus(0);
        noti.setTitle("Success");
        noti.setDetail("Waiting for Server accept ☺");
        client.sendEvent("NOTIFICATE",noti);
    }

    public void statusWarming(SocketIOClient client){
        NotificationExtracter noti = new NotificationExtracter();
        noti.setStatus(1);
        noti.setTitle("Warming");
        noti.setDetail("Character is wrong \nCheck character again");
        client.sendEvent("NOTIFICATE",noti);
    }

}
