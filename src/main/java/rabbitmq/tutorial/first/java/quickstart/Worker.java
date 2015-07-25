package rabbitmq.tutorial.first.java.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;

/**
 * Created by g1a@pdl on 2015/7/26.
 */
public class Worker {
    private final static String QUEUE_NAME="task_durable";

    public static void main(String[] args) throws IOException, InterruptedException {

        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection=factory.newConnection();
        Channel channel=connection.createChannel();

        boolean durable = true;
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
        System.out.println("[*] waiting for messages.To exit press ctrl+z");

        QueueingConsumer consumer=new QueueingConsumer(channel);
        boolean autoAck=false;
        channel.basicConsume(QUEUE_NAME,false,consumer);

        // ��ƽ�ַ�
        // ��֪RabbitMQ��Ҫͬʱ��һ�������߳���һ������
        // 1�������ߴ�����ɣ�����ȷ��֮ǰ��Ҫ�����ַ�һ���µ���Ϣ
        int prefetchcount=1;
        channel.basicQos(prefetchcount);

        while (true){
            QueueingConsumer.Delivery delivery=consumer.nextDelivery();
            String message =new String(delivery.getBody());
            System.out.println(" [x] Received '"+message +"'");
            // ģ�⹤��
            doWork(message);
            System.out.println(" [x] Done");
            // ����ȷ����Ϣ
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);

        }

    }
    private static void doWork(String task) throws InterruptedException {
        for (char ch: task.toCharArray()){
            if(ch=='.') {
                System.out.println("loading");
                Thread.sleep(1000);
            }
        }

    }

}
