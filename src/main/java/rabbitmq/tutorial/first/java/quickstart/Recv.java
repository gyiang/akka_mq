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
        // 打开一个连接和一个通道，声明一个我们要消费的队列。注意要与发送的队列相匹配。
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection=factory.newConnection();
        Channel channel=connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("[*] waiting for messages.To exit press ctrl+z");

        // 回调对象用来缓存消息 直到我们准备好再使用它们
        QueueingConsumer consumer=new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME,false,consumer);

        while (true){
            //QueueingConsumer.nextDelivery()在另一个来自服务器的消息到来之前它会一直阻塞着。
            QueueingConsumer.Delivery delivery=consumer.nextDelivery();
            String message =new String(delivery.getBody());
            System.out.println(" [x] Received '"+message +"'");

        }

    }

}
