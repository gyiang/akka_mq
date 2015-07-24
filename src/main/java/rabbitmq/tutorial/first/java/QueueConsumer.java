package rabbitmq.tutorial.first.java;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import org.apache.commons.lang.SerializationUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by g1a@pdl on 2015/7/23.
 */

/**
 * read queue
 * @author syntx
 *
 */
public class QueueConsumer extends EndPoint implements Runnable,Consumer{
    static int cnt=0;
    public QueueConsumer(String endPointName) throws IOException {
        super(endPointName);
    }

    @Override
    public void run() {
        try {
            //start consuming messages. Auto acknowledge messages.
            channel.basicConsume(endPointName,true,this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when consumer is registered.
     */
    @Override
    public void handleConsumeOk(String consumerTag) {
        System.out.println("Consumer "+consumerTag +" registered");

    }

    /**
     * Called when new message is available.
     */
    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] body) throws IOException {
        Map map= (HashMap) SerializationUtils.deserialize(body);
        System.out.println("Message Number "+ map.get("message number") + " received.");
        synchronized (this){
            cnt++;
        }

    }


    @Override
    public void handleCancelOk(String s) {

    }

    @Override
    public void handleCancel(String s) throws IOException {

    }

    @Override
    public void handleShutdownSignal(String s, ShutdownSignalException e) {

    }

    @Override
    public void handleRecoverOk(String s) {

    }



}
