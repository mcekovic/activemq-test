package test.activemq.camel;

import javax.jms.*;

import org.apache.activemq.*;

public class AdHocSendMessage {

	public static void main(String[] args) throws JMSException {
//		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://sysintnode5.beg.finsoft.com:61616");
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://172.26.236.16:61616");
		Connection conn = factory.createConnection();
		try {
			Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			try {
				Queue queue = session.createQueue("tsm-ts-rwms");
				MessageProducer producer = session.createProducer(queue);
				producer.setDeliveryMode(DeliveryMode.PERSISTENT);
				try {
					String body = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
						"<ns2:MarketResult xmlns:ns2=\"http://www.gtech.com/wagerwise/tsm-bo\">\n" +
						"<tradingSegmentsInfo><tradingSegmentId>2</tradingSegmentId><channelIds>2</channelIds><channelIds>3</channelIds><channelIds>4</channelIds></tradingSegmentsInfo>\n" +
						"<id>5021542</id><marketId>30616278</marketId><dataSourceId>SOGEI</dataSourceId><eventId>1020217</eventId><competitionId>22870</competitionId><sportId>TNS</sportId>\n" +
						"<partial>false</partial><inPlay>true</inPlay>\n" +
						"<makeUpValue xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/><makeUpValueEarly xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" +
						"<valid>true</valid><official>true</official><version>1</version><marketVersion>21</marketVersion>\n" +
						"<selectionResults><id>10115308</id><selectionId>66506037</selectionId><resultValue>1</resultValue><resultValueEarly xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/><valid>true</valid></selectionResults>\n" +
						"</ns2:MarketResult>";
					TextMessage message = session.createTextMessage(body);
					message.setStringProperty("MessageType", "MarketResultMsgTS");
					producer.send(message);
				}
				finally {
					producer.close();
				}
			}
			finally {
				session.close();
			}
		}
		finally {
			conn.close();
		}
	}
}
