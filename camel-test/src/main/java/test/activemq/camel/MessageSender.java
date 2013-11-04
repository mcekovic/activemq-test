package test.activemq.camel;

import javax.jms.*;

import org.slf4j.*;
import org.springframework.jms.core.*;

public class MessageSender {

	private final JmsTemplate jmsTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageSender.class);

	public MessageSender(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void init() {
		jmsTemplate.send(new TestMessageCreator("test1", "Test1"));
		jmsTemplate.send(new TestMessageCreator("test2", "Test2"));
	}

	private static class TestMessageCreator implements MessageCreator {

		private final String type;
		private final String text;

		private TestMessageCreator(String type, String text) {
			this.type = type;
			this.text = text;
		}

		@Override public Message createMessage(Session session) throws JMSException {
			TextMessage message = session.createTextMessage(text);
			message.setStringProperty("type", type);
			LOGGER.info("Message created: " + message);
			return message;
		}
	}
}
