import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import org.apache.log4j.BasicConfigurator;

import javax.jms.JMSException;

// activemq
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import eventhandler.ConnectEvent;
import eventhandler.DisconnectEvent;
import eventhandler.EventListen;
import dataextracter.RegisterExtracter;

public class main {

    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    // default broker URL is : tcp://localhost:61616"
    private static String subjectRegister = "REGISTER";
    private static String subject = "JCG_QUEUE";

    public static void main(String args[])  throws JMSException{
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        //Creating a non transactional session to send/receive JMS message.
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);

        //Destination represents here our queue 'JCG_QUEUE' on the JMS server.
        //The queue will be created automatically on the server.
        Destination destinationRegister = session.createQueue(subjectRegister);
        Destination destination = session.createQueue(subject);

        // MessageProducer is used for sending messages to the queue.
        MessageProducer producerRegister = session.createProducer(destinationRegister);
        MessageProducer producer = session.createProducer(destination);

        //activeMQ
        BasicConfigurator.configure();

        Configuration config = new Configuration();
        config.setHostname("0.0.0.0");
        config.setPort(3700);
        //config.setStoreFactory(); how to use this

        SocketIOServer server = new SocketIOServer(config);

        server.addConnectListener(new ConnectEvent());
        server.addDisconnectListener(new DisconnectEvent());
        server.addEventListener("REGISTER", RegisterExtracter.class, new EventListen(session, producerRegister));

        server.start();
    }
}
