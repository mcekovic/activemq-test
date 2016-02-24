package test.activemq.camel;

import java.util.concurrent.*;

import org.apache.camel.*;

public class DLQProcessor {

	private CamelContext context;

	public void setContext(CamelContext context) {
		this.context = context;
	}

	public void process() throws Exception {
		System.out.println("Starting DLQ route...");
		context.startRoute("dlqRoute");
		TimeUnit.SECONDS.sleep(10L);
		context.stopRoute("dlqRoute");
		System.out.println("DLQ route stopped.");

		System.out.println("Starting Staging DLQ route...");
		context.startRoute("stagingDlqRoute");
		TimeUnit.SECONDS.sleep(10L);
		context.stopRoute("stagingDlqRoute");
		System.out.println("Staging DLQ route stopped.");
	}
}
