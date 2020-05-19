package user.sender.data;

import message.Messager;
import message.MessagerInstance;
import session.SessionStore;

import javax.jms.JMSException;
import java.util.Timer;
import java.util.TimerTask;

public class ScheduleLoop extends TimerTask {

    @Override
    public void run() {
        SessionStore.getInstance().syncSession();
    }
}
