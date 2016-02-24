package test.activemq.durable;

import javax.jms.*;

import org.apache.activemq.*;

public class UnsubscribeDurableSubscriber {

	private static final String CLIENT_ID = "LDS_tsm-bo_12953@LXRP-IT-WWLDSTA-1";
	private static final String DURABLE_TOPIC_NAME = "DurableLDSTopic.tsm-bo.LXRP-IT-WWLDSTA-1";

	public static void main(String[] args) throws JMSException {
		ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://172.26.236.16:61616");
		Connection c = cf.createConnection();
		try {
			c.setClientID(CLIENT_ID);
			Session s = c.createSession(true, Session.AUTO_ACKNOWLEDGE);
			try {
				s.unsubscribe(DURABLE_TOPIC_NAME);
			}
			finally {
				s.close();
			}
		}
		finally {
			c.close();
		}
	}
}
