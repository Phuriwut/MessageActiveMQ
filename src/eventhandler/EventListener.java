package eventhandler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import session.SessionStore;
import session.SessionStoreInstance;
import Message.*;

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
    public abstract void onData(SocketIOClient socketIOClient, T registerExtracter, AckRequest ackRequest) throws Exception;
}
