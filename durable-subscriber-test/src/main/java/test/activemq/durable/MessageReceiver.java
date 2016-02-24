package test.activemq.durable;

import java.util.concurrent.*;
import javax.jms.*;

import org.slf4j.*;
import org.springframework.context.support.*;

public class MessageReceiver implements MessageListener {

	public static void main(String[] args) throws InterruptedException {
		GenericXmlApplicationContext context = new GenericXmlApplicationContext("consumer.xml");
		context.registerShutdownHook();
		Thread.sleep(TimeUnit.DAYS.toMillis(1L));
	}


	private String name;
	private long t0;
	private int received;

	private static final int STEP = 100;
	private static final long SLEEP = 0L;

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiver.class);

	public void setName(String name) {
		this.name = name;
	}

	@Override public void onMessage(Message message) {
		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(message.toString());
				LOGGER.info(name + ": " + ((TextMessage)message).getText());
			}
			if (t0 == 0L)
				t0 = System.currentTimeMillis();
			if (++received % STEP == 0) {
				long dt = System.currentTimeMillis() - t0;
				System.out.printf("Received by %s: %d in %d ms (%.1f msg/s)%n", name, received, dt, received*1000.0/dt);
			}
			if (SLEEP > 0)
				Thread.sleep(SLEEP);
		}
		catch (JMSException | InterruptedException ex) {
			LOGGER.error("Error receiving message.", ex);
		}
	}
}
