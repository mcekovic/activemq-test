package test.activemq.camel;

import org.springframework.context.support.*;

public class ActiveMQBroker {

	public static void main(String[] args) throws InterruptedException {
		new GenericXmlApplicationContext("active-mq.xml").registerShutdownHook();
	}
}
