package rabbitmq.tutorial.first.java;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * Created by g1a@pdl on 2015/7/23.
 */
public abstract class EndPoint {
    protected Channel channel;
    protected Connection connection;
    protected String endPointName;

    public EndPoint(String endPointName) throws IOException {
        // set endPointName
        this.endPointName=endPointName;

        //Create a connection factory
        ConnectionFactory factory=new ConnectionFactory();

        //hostname of your rabbitmq server
        factory.setHost("localhost");

        //getting a connection
        connection=factory.newConnection();

        //creating a channel
        channel=connection.createChannel();

        //declaring a queue for this channel. If queue does not exist,
        //it will be created on the server.
        channel.queueDeclare(endPointName,false,false,false,null);

    }

    /**
     * close channel&connection¡£not must,auto close
     * @throws IOException
     */
    public void close() throws IOException {
        this.channel.close();
        this.connection.close();
    }




}
