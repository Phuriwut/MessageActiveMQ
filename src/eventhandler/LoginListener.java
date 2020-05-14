package eventhandler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import dataextracter.LoginExtracter;
import dataextracter.NotificationExtracter;
import org.json.JSONObject;

public class LoginListener extends EventListener<LoginExtracter> {
    public LoginListener(){
        super();
    }

    @Override
    public void onData(SocketIOClient client, LoginExtracter loginExtracter, AckRequest ackRequest) throws Exception {
        loginExtracter.setSessionID(client.getSessionId());
//        System.out.println("Email: " + loginExtracter.getEmail());
//        System.out.println("Password: " +loginExtracter.getPassword());
//        System.out.println("SessionID : " + client.getSessionId());


        // We will send a small text message saying 'Hello World!!!'
        if(true) {
            JSONObject obj = new JSONObject();
            obj.put("type", "LOGIN");
            obj.put("data",loginExtracter.toString());
            this.messager.send(obj.toString());
            System.out.println("------------------------------------------\nMessage LOGIN::: '" + loginExtracter.toString()+ "'\n------------------------------------------");
        }else {
            statusWarming(client);
        }
    }

    public void statusWarming(SocketIOClient client){
        NotificationExtracter noti = new NotificationExtracter();
        noti.setStatus(1);
        noti.setTitle("Check Please ");
        noti.setDetail("email or password is wrong –");
    }

}

