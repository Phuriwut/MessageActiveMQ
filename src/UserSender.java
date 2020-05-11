import Message.Messager;
import Message.MessagerInstance;
import UserSenderData.*;
import com.google.gson.Gson;
import session.SessionData;
import session.SessionStore;
import session.SessionStoreInstance;

import javax.jms.JMSException;

public class UserSender implements  Runnable{
    SessionStoreInstance session = SessionStore.getInstance();
    MessagerInstance messager;
    Gson gson = new Gson();
    public UserSender() throws JMSException {
        this.messager = Messager.getInstance();
    }

    @Override
    public void run() {
        while(true){
            String message;
            try {
                message = this.messager.recieve();
            } catch (JMSException e) {
                e.printStackTrace();
                continue;
            }
            System.out.println(message);
            Response res = this.gson.fromJson(message, Response.class);
            SessionData sessionData = this.session.getSessionData(res.getSessionID());
            String event = res.getType();
            String data = res.getData();
            System.out.println(event);
            System.out.println(data);
            System.out.println(res.getSessionID());
            sessionData.getClient().sendEvent(event, data);
        }
    }
}
