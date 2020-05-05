import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import org.apache.log4j.BasicConfigurator;

import javax.jms.JMSException;

// activemq
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class main_test {

    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    // default broker URL is : tcp://localhost:61616"
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
        Destination destination = session.createQueue(subject);

        // MessageProducer is used for sending messages to the queue.
        MessageProducer producer = session.createProducer(destination);

        //activemq
        BasicConfigurator.configure();

        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(3700);

        SocketIOServer server = new SocketIOServer(config);

        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                HandshakeData hcd = client.getHandshakeData();
                System.out.println("connected : " + client.getSessionId().toString());
                System.out.println("namespace :" + client.getNamespace().getName());
                System.out.println("url : " + hcd.getUrl());
                System.out.println("header : " + hcd.getHttpHeaders().toString());

            }
        });

        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                System.out.println("disconnected " + client.getSessionId().toString());
            }
        });
        server.addEventListener("send", Message.class, new DataListener<Message>() {

            @Override
            public void onData(SocketIOClient client, Message data, AckRequest ackSender) throws Exception {
                System.out.println("onSend: " + data.toString());
                // We will send a small text message saying 'Hello World!!!'
                TextMessage message = session
                        .createTextMessage( data.toString());

                // Here we are sending our message!
                producer.send(message);

                System.out.println("JCG printing@@ '" + message.getText() + "'");
            }
        });

        server.start();
    }
}
