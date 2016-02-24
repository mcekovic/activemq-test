package test.activemq.durable;

import javax.jms.*;

import org.slf4j.*;
import org.springframework.context.support.*;
import org.springframework.jms.core.*;

public class MessageSender {

	public static void main(String[] args) throws InterruptedException {
		GenericXmlApplicationContext context = new GenericXmlApplicationContext("producer.xml");
		context.registerShutdownHook();
		((MessageSender)context.getBean("messageSender")).send();
	}

	private final JmsTemplate jmsTemplate;

	private static final int COUNT = 200000;
	private static final int STEP = 1000;
	private static final long SLEEP = 0L;

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageSender.class);

	public MessageSender(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void send() throws InterruptedException {
		long t0 = System.currentTimeMillis();
		for (int i = 1; i <= COUNT; i++) {
			if (SLEEP > 0)
				Thread.sleep(SLEEP);
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
