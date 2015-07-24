package rabbitmq.tutorial.first.java;

import org.apache.commons.lang.SerializationUtils;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by gya@pdl on 2015/7/23.
 */

/**
 * The producer endpoint that writes to the queue.
 * @author syntx
 */
public class Producer extends EndPoint {
    public Producer(String endPointName) throws IOException {
        super(endPointName);
    }

    public void sendMessage(Serializable object) throws IOException {
        channel.basicPublish("",endPointName,null, SerializationUtils.serialize(object));

    }
}
