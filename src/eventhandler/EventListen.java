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

        // We will send a small text message saying 'Hello World!!!'
        if(registerExtracter.getCareer() < 5 &&
                registerExtracter.getBank_id().length() == 12 &&
                registerExtracter.getBank_name() < 6) {
            TextMessage message = this.session
                    .createTextMessage(registerExtracter.toString());

            // Here we are sending our message!
            this.producerRegister.send(message);
            statusSuccess(client);

            System.out.println("Message Register::: '" + message.getText() + "'");
        }else {
            statusWarming(client);
        }
    }

    public void statusSuccess(SocketIOClient client){
        NotificationExtracter noti = new NotificationExtracter();
        noti.setStatus(0);
        noti.setTitle("Success");
        noti.setDetail("Welcome to my world!!");
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
