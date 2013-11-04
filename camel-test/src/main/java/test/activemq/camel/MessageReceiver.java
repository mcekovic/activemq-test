package test.activemq.camel;

import javax.jms.*;

import org.slf4j.*;

public class MessageReceiver implements MessageListener {

	private String name;

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiver.class);

	public void setName(String name) {
		this.name = name;
	}

	@Override public void onMessage(Message message) {
		try {
			LOGGER.info(message.toString());
			LOGGER.info(name + ": " + ((TextMessage)message).getText());
		}
		catch (JMSException ex) {
			LOGGER.error("Error receiving message.", ex);
		}
	}
}
