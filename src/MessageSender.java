import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class MessageSender {

    //URL of the JMS server. DEFAULT_BROKER_URL will just mean that JMS server is on localhost
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    // default broker URL is : tcp://localhost:61616"
    private static String subject = "JCG_QUEUE"; // Queue Name.You can create any/many queue names as per your requirement.

    ConnectionFactory connectionFactory;
    Connection connection;
    Session session;
    Destination destination;
    MessageProducer producer;

    public void MessageSender() throws JMSException {
        // Getting JMS connection from the server and starting it
        System.out.println("JCG printing@@ 'iceiceicieciecie'");
        this.connectionFactory = new ActiveMQConnectionFactory(url);
        this.connection = connectionFactory.createConnection();
        this.connection.start();

        //Creating a non transactional session to send/receive JMS message.
        this.session = this.connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);

        //Destination represents here our queue 'JCG_QUEUE' on the JMS server.
        //The queue will be created automatically on the server.
         this.destination = this.session.createQueue(subject);

        // MessageProducer is used for sending messages to the queue.
        this.producer = this.session.createProducer(destination);
        this.sendData("cscsdcsdcsdcsdcdscadc");
    }
    public void close() throws JMSException  {
        this.connection.close();
    }

    public void sendData(String data) throws JMSException  {
        // We will send a small text message saying 'Hello World!!!'
        TextMessage message = this.session.createTextMessage(data);

        // Here we are sending our message!

        this.producer.send(message);

        System.out.println("JCG printing@@ '" + message.getText() + "'");
    }

}