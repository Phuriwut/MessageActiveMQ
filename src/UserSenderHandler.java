import Message.MessagerInstance;
import UserSenderData.Response;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import session.SessionData;
import session.SessionStore;
import session.SessionStoreInstance;

public class UserSenderHandler {
    SessionStoreInstance session;
    MessagerInstance messager;

    public UserSenderHandler(SessionStoreInstance session, MessagerInstance messager) {
        this.session = session;
        this.messager = messager;
    }

    public void passData(Response res){
        SessionData sessionData = this.session.getSessionData(res.getSessionID());
        String event = res.getType();
        String data = res.getData();
        sessionData.getClient().sendEvent(event, data);
    }

    public void  loginData(Response res){
        String data = res.getData();
        JsonParser jsonParser = new JsonParser();
        JsonObject objectFromString = jsonParser.parse(data).getAsJsonObject();
        SessionData sessionData = this.session.getSessionData(res.getSessionID());
        sessionData.setFirstname(objectFromString.get("firstname").getAsString());
        sessionData.setLastname(objectFromString.get("lastname").getAsString());
        sessionData.setEmail(objectFromString.get("email").getAsString());
        sessionData.setAge(objectFromString.get("age").getAsInt());
        sessionData.setCareer(objectFromString.get("career").getAsInt());
        sessionData.setIncome(objectFromString.get("income").getAsInt());
        sessionData.setBank_id(objectFromString.get("bank_id").getAsString());
        sessionData.setBank_name(objectFromString.get("bank_name").getAsInt());
        System.out.println(sessionData.isLogin());
        sessionData.setLogin(true);
        System.out.println(sessionData.isLogin());

        this.passData(res);
    }

}
