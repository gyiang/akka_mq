package rabbitmq.tutorial.first.java.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;

/**
 * Created by g1apdl on 2015/7/26.
 */
public class NewTask {

    private final static String QUEUE_NAME="task_durable";

    public static void main(String[] args) throws IOException {

        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection=factory.newConnection();
        Channel channel=connection.createChannel();

        // ���г־û�
        boolean durable = true;
        channel.queueDeclare(QUEUE_NAME,durable,false,false,null);
        String message=getMessage(args);

        // ��Ϣ�־û� null->PERSISTENT_TEXT_PLAIN
        // �ǽ�׳�ĳ־û���֤�����ܵ�����Ϣ��ʧ
        channel.basicPublish("",QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");




    }

    private static String getMessage(String[] args) {
        if (args.length<1)
            return "hello world..!";
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
