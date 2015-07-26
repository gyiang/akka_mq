package rabbitmq.tutorial.first.java.quickstart.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;

/**
 * Created by g1a@pdl on 2015/7/26.
 */
public class EmitLog {

    private final  static String EXECHANGE_NAME="logs";

    public static void main(String[] args) throws IOException {
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection=factory.newConnection();
        Channel channel=connection.createChannel();

        // rabbitmqctl list_exchanges
        // 交换类型：direct，topic，deaders，fanout
        // fanout：消息广播
        channel.exchangeDeclare(EXECHANGE_NAME,"fanout");

        String message=getMessage(args);

        //1交换机名 2队列名 3持久化标志 4消息
        channel.basicPublish(EXECHANGE_NAME,"", null,message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();

    }

    private static String getMessage(String[] args) {
        if (args.length<1)
            return "hello world 123!";
        return joinString(args," ");
    }

    private static String joinString(String[] args, String delimiter) {
        int length = args.length;
        if (length == 0) return "";
        StringBuilder words = new StringBuilder(args[0]);
        for (int i = 1; i < length; i++) {
            words.append(delimiter).append(args[i]);
        }
        return words.toString();
    }


}
