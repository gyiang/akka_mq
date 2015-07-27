package rabbitmq.tutorial.first.java.quickstart.router;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * Created by g1a@pdl on 2015/7/27.
 */
public class EmitLogDirect {
    private static final String EXCHANGE_NAME="direct_logs";

    public static void main(String[] args) throws IOException {
        ConnectionFactory factory =new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection=factory.newConnection();
        Channel channel=connection.createChannel();

        // direct
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");
        
        String severity =getSeverity(args);
        String message =getMessage(args);

        channel.basicPublish(EXCHANGE_NAME,severity,null,message.getBytes());
        System.out.println(" [x] Sent '" + severity + "':'" + message + "'");

        channel.close();
        connection.close();


    }

    private static String getMessage(String[] strings) {
        
        if (strings.length < 2)
            return "Hello World!";
        return joinStrings(strings, " ", 1);
    }

    private static String joinStrings(String[] strings, String delimiter, int startIndex) {
        int length = strings.length;
        if (length == 0 ) return "";
        if (length < startIndex ) return "";
        StringBuilder words = new StringBuilder(strings[startIndex]);
        for (int i = startIndex + 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }

    private static String getSeverity(String[] args) {
        if (args.length < 1)
            return "info";
        return args[0];
    }


}
