import constance.events.ClientEvents;
import message.Messager;
import message.MessagerInstance;
import user.sender.data.*;
import com.google.gson.Gson;
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
        UserSenderHandler ush = new UserSenderHandler(session, messager);
        while(true){
            String message;
            try {
                message = this.messager.recieve();
            } catch (JMSException e) {
                e.printStackTrace();
                continue;
            }

            Response res = this.gson.fromJson(message, Response.class);
            String event = res.getType();
            System.out.println(event);
            if(event.equals(ClientEvents.LOGIN_RECEIVE.getString())){
                ush.loginData(res);
            }else {
                ush.passData(res);
            }

        }
    }
}
