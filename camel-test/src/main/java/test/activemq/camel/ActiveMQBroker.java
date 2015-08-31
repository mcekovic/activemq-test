package test.activemq.camel;

import org.springframework.context.support.*;

public class ActiveMQBroker {

	public static void main(String[] args) throws InterruptedException {
		GenericXmlApplicationContext context = new GenericXmlApplicationContext("active-mq.xml");
		context.registerShutdownHook();
		((MessageSender)context.getBean("messageSender")).send();
	}
}
