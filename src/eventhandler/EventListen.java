package eventhandler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import dataextracter.NotificationExtracter;
import dataextracter.RegisterExtracter;

import javax.jms.*;

public class EventListen implements DataListener<RegisterExtracter> {
    Session session;
    MessageProducer producerRegister;

    public EventListen(Session session, MessageProducer producerRegister){
        this.session = session;
        this.producerRegister = producerRegister;
    }


    @Override
    public void onData(SocketIOClient client, RegisterExtracter registerExtracter, AckRequest ackRequest) throws Exception {
        System.out.println("Firstname: " + registerExtracter.getFirstname());
        NotificationExtracter noti = new NotificationExtracter();
        noti.setStatus(0);
        noti.setTitle("success");
        noti.setDetail("ice ice ice");
        client.sendEvent("NOTIFICATE",noti);
        // We will send a small text message saying 'Hello World!!!'
        if(registerExtracter.getCareer() < 5 &&
                registerExtracter.getBank_id().length() < 13 &&
                registerExtracter.getBank_name() < 6) {
            TextMessage message = this.session
                    .createTextMessage(registerExtracter.toString());


            // Here we are sending our message!
            this.producerRegister.send(message);

            System.out.println("Message Register::: '" + message.getText() + "'");
        }
    }

}
