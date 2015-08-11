package test.activemq.camel;

import javax.jms.*;

import org.slf4j.*;
import org.springframework.jms.core.*;

public class MessageSender {

	private final JmsTemplate jmsTemplate;

	private static final int COUNT = 1000;
	private static final int STEP = 100;

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageSender.class);

	public MessageSender(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void init() {
		long t0 = System.currentTimeMillis();
		for (int i = 1; i <= COUNT; i++) {
			jmsTemplate.send(new TestMessageCreator("test1", "Test1"));
			jmsTemplate.send(new TestMessageCreator("test2", "Test2"));
			if (i % STEP == 0) {
				int sent = i * 2;
				long dt = System.currentTimeMillis() - t0;
				System.out.printf("Sent: %d in %d ms (%.1f msg/s)%n", sent, dt, sent*1000.0/dt);
			}
		}
		System.out.println("Sending finished.");
	}

	public int getSendCount() {
		return COUNT;
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
