package com.javashitang.rabbitmq.enjoy.ackfalse;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.javashitang.rabbitmq.enjoy.exchange.direct.DirectProducer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: lilimin
 * @Date: 2019/8/26 23:30
 */
public class AckFalseConsumerA {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("www.erlie.cc");

        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        channel.exchangeDeclare(DirectProducer.EXCHANGE_NAME, "direct");

        String queueName = "focuserror";
        channel.queueDeclare(queueName, false, false, false, null);

        String bindingKey = "error";
        channel.queueBind(queueName, DirectProducer.EXCHANGE_NAME, bindingKey);

        final Consumer consumer = new DefaultConsumer(channel) {
            @Override public void handleDelivery(String consumerTag, Envelope envelope,
                AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(envelope.getRoutingKey() + " " + message);
            }
        };

        channel.basicConsume(queueName, false, consumer);
    }
}