package eventhandler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import constance.events.ClientEvents;
import session.SessionData;
import session.SessionStore;
import session.SessionStoreInstance;
import message.*;

import javax.jms.*;
import java.util.UUID;

public abstract class EventListener<T> implements DataListener<T> {
    MessagerInstance messager;
    SessionStoreInstance sessionstore;

    public EventListener(){
        this.sessionstore = SessionStore.getInstance();
        try {
            this.messager = Messager.getInstance();
        }catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onData(SocketIOClient client, T registerExtracter, AckRequest ackRequest) throws Exception{
        SessionData userSession= this.sessionstore.getSessionData(client.getSessionId().toString());
        if(userSession != null){
            userSession.setClient(client);
            System.out.println(userSession.isLogin());
            if(userSession.isLogin()) client.sendEvent(ClientEvents.RECEIVE_PROFILE.getString(), userSession.toString());
        }else{
            this.sessionstore.addSessionData(client.getSessionId().toString(),new SessionData(client));
        }
        handler(client, registerExtracter, ackRequest);
    }

    public abstract void handler(SocketIOClient socketIOClient, T registerExtracter, AckRequest ackRequest) throws JMSException;
}
