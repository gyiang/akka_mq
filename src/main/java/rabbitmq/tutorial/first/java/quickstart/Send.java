package rabbitmq.tutorial.first.java.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import scala.util.parsing.combinator.testing.Str;

import java.io.IOException;

/**
 * Created by g1a@pdl on 2015/7/24.
 */
public class Send {
    private final static  String QUEUE_NAME="hello";

    public static void main(String[] args) throws IOException {
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection=factory.newConnection();
        Channel channel=connection.createChannel();

        // ����һ�����Ͷ���
        // ����һ���������ݵȵģ�������Ҫ�����Ķ��в�����ʱ�Ŵ�����
        // ��Ϣ�����Ƕ��������飬�������������ϲ�ñ��롣
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        // ������Ϣ
        String message="hello world";
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();

    }

}
