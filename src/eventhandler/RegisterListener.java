package eventhandler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import dataextracter.NotificationExtracter;
import dataextracter.RegisterExtracter;
import org.json.JSONObject;

public class RegisterListener extends  EventListener<RegisterExtracter> {
    public RegisterListener(){
        super();
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

            JSONObject obj = new JSONObject();
            obj.put("type", "REGISTER");
            obj.put("data", registerExtracter.toString());
            this.messager.send(obj.toString());

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
        client.sendEvent("NOTIFICATE",noti.toString());
    }

    public void statusWarming(SocketIOClient client){
        JSONObject noti = new JSONObject();
        noti.put("status", 1);
        noti.put("title", "Warming");
        noti.put("detail","Character is wrong\nCheck character again ");
        System.out.println(noti.toString());
        client.sendEvent("NOTIFICATE",noti.toString());
    }
}
