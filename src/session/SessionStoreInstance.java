package session;

import java.util.HashMap;
import java.util.UUID;

public class SessionStoreInstance {
    HashMap<String , SessionData> sessionmap = new HashMap<String, SessionData>();

    public void addSessionData(String uuid, SessionData sessiondata){
        this.sessionmap.put(uuid.toString(), sessiondata);
    }

    public SessionData getSessionData(String uuid){
        return this.sessionmap.get(uuid);
    }
    public void removeSessionData(String uuid){
        sessionmap.remove(uuid);
    }
}
