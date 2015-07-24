package rabbitmq.tutorial.first.java;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by gya@pdl on 2015/7/23.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        QueueConsumer consumer=new QueueConsumer("queue");
        Thread consumerThread=new Thread(consumer);
        consumerThread.start();

        Producer producer=new Producer("queue");

        long startTime=System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            HashMap message=new HashMap();
            message.put("message number",i);
            producer.sendMessage(message);
            System.out.println("Message Number "+i+" send.");
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println((System.currentTimeMillis()-startTime)/1000.0 + "s");
        System.out.println(QueueConsumer.cnt);

    }


}
