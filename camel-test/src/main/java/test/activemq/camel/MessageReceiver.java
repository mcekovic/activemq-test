package test.activemq.camel;

import javax.jms.*;

import org.slf4j.*;

public class MessageReceiver implements MessageListener {

	private MessageSender sender;
	private String name;
	private double receiveRatio = 1.0;
	private long t0;
	private int received;

	private static final int STEP = 100;

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiver.class);

	public void setSender(MessageSender sender) {
		this.sender = sender;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setReceiveRatio(double receiveRatio) {
		this.receiveRatio = receiveRatio;
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
			if (received == sender.getSendCount()*receiveRatio)
				System.out.printf("Receive by %s finished.%n", name);
		}
		catch (JMSException ex) {
			LOGGER.error("Error receiving message.", ex);
		}
	}
}
