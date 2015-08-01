package rabbitmq.tutorial.first.java.quickstart.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import rabbitmq.tutorial.first.java.QueueConsumer;

import java.io.IOException;

/**
 * Created by g1a@pdl on 2015/7/26.
 */
public class ReceiveLog {
    private static final String EXCHANGE_NAME = "logs";


    public static void main(String[] args) throws IOException, InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        // 声明一个随机名称的队列,随机队列名在宕机后不
        String queueName=channel.queueDeclare().getQueue();
        // 绑定交互机
        channel.queueBind(queueName,EXCHANGE_NAME,"");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+Z");

        QueueingConsumer consumer=new QueueingConsumer(channel);
        channel.basicConsume(queueName,true,consumer);

        int cnt=1;
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message.substring(0,10) + "'");
        }


    }


}
