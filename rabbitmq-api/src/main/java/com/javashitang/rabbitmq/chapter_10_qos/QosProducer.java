package com.javashitang.rabbitmq.chapter_10_qos;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class QosProducer {

		public static final String EXCHANGE_NAME = "msg_durable";

		public static void main(String[] args) throws IOException, TimeoutException {
				ConnectionFactory factory = new ConnectionFactory();
				factory.setHost("www.javashitang.com");

				Connection connection = factory.newConnection();
				Channel channel = connection.createChannel();
				channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

				String routingKey = "info";
				String message = "hello rabbitmq";

				for (int i = 0; i < 10; i++) {
						channel.basicPublish(EXCHANGE_NAME, routingKey, true, null, message.getBytes());
				}
				channel.close();
				connection.close();
		}
}