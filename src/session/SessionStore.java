package session;

import java.util.HashMap;
import java.util.UUID;


public class SessionStore{
    private static SessionStoreInstance ssi;

    public static SessionStoreInstance getInstance(){
        if(ssi == null){
            ssi = new SessionStoreInstance();
        }
        return ssi;
    }

}