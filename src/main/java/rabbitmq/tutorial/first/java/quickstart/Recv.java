package rabbitmq.tutorial.first.java.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import rabbitmq.tutorial.first.java.QueueConsumer;

import java.io.IOException;

/**
 * Created by g1a@pdl on 2015/7/24.
 */
public class Recv {
    private final static String QUEUE_NAME="hello";

    public static void main(String[] args) throws IOException, InterruptedException {
        // ��һ�����Ӻ�һ��ͨ��������һ������Ҫ���ѵĶ��С�ע��Ҫ�뷢�͵Ķ�����ƥ�䡣
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection=factory.newConnection();
        Channel channel=connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("[*] waiting for messages.To exit press ctrl+z");

        // �ص���������������Ϣ ֱ������׼������ʹ������
        QueueingConsumer consumer=new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME,false,consumer);

        while (true){
            //QueueingConsumer.nextDelivery()����һ�����Է���������Ϣ����֮ǰ����һֱ�����š�
            QueueingConsumer.Delivery delivery=consumer.nextDelivery();
            String message =new String(delivery.getBody());
            System.out.println(" [x] Received '"+message +"'");

        }

    }

}
