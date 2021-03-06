package schedulerTests;

import static org.junit.Assert.assertEquals;
import gateway.GenericGateway;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import concurrency.Consumer;
import message.Message;
import message.StringMessage;
import dataSructures.MessageStorage;

/**
 * Unit tests for Consumer class
 */
public class ConsumerTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerTest.class);	
	protected static final GenericGateway GENERIC_GATEWAY = new GenericGateway();
	private final static StringMessage POISON = new StringMessage(-1,"POISON"); 
	private BlockingQueue<Message> queue;
	private MessageStorage messageStorage;
	private Consumer consumer;
	private Message msg1; 
	private Message msg2; 
	private Message msg3; 
	private Message msg4;  

	@Before
	public void setUp() throws Exception {
		queue = new LinkedBlockingQueue<Message>();
		messageStorage = new MessageStorage();
		consumer = new Consumer(queue, messageStorage, GENERIC_GATEWAY);

		msg1 = new StringMessage(2, "msg1");
		msg2 = new StringMessage(1, "msg2");
		msg3 = new StringMessage(2, "msg3");
		msg4 = new StringMessage(3, "msg4");

		queue.put(msg1);
		messageStorage.addMessageToStorage(msg1);
		queue.put(msg2);
		messageStorage.addMessageToStorage(msg2);
		queue.put(msg3);
		messageStorage.addMessageToStorage(msg3);
		queue.put(msg4);
		messageStorage.addMessageToStorage(msg4);
		queue.put(POISON);
	}

	/**
	 * Test the scheduling of the messages  
	 * Expected send sequence to Gateway: msg1 -> msg3 -> msg2 -> msg4
	 * Please compare the console output with the above sequence of messages as well
	 * */
	@Test
	public void testProducerFromSpecSheet(){
		LOGGER.info("testProducerFromSpecSheet() ===================================");
		consumer.run();
		assertEquals(0, messageStorage.size());
	}

	/**
	 * Test the scheduling of the messages  
	 * Expected send sequence to Gateway: msg1 -> msg1 -> msg1 -> msg2 ->msg4
	 * @throws InterruptedException 
	 * @throws CloneNotSupportedException 
	 * */
	@Test
	public void testOneProducerThreetoTwo() throws InterruptedException, CloneNotSupportedException {

		LOGGER.info("testOneProducerThreetoTwo() ===================================");

		queue.clear();
		messageStorage.clear();

		queue.put(msg1);
		messageStorage.addMessageToStorage(msg1);
		queue.put(msg2);
		messageStorage.addMessageToStorage(msg2);
		queue.put((Message) msg1.clone());
		messageStorage.addMessageToStorage((Message) msg1.clone());
		queue.put((Message) msg1.clone());
		messageStorage.addMessageToStorage((Message) msg1.clone());
		queue.put(msg4);
		messageStorage.addMessageToStorage(msg4);
		queue.put(POISON);		
		
		consumer.run();
	}

	/**
	 * Test the scheduling of the messages  
	 * Expected send sequence to Gateway: msg2 -> msg1 -> msg1 -> msg1
	 * @throws InterruptedException 
	 * @throws CloneNotSupportedException 
	 * */
	@Test
	public void testOneProducerOnetoThree() throws InterruptedException, CloneNotSupportedException {

		LOGGER.info("testOneProducerOnetoThree() ===================================");

		queue.clear();
		messageStorage.clear();

		queue.put(msg2);
		messageStorage.addMessageToStorage(msg2);
		queue.put(msg1);
		messageStorage.addMessageToStorage(msg1);
		queue.put((Message) msg1.clone());
		messageStorage.addMessageToStorage((Message) msg1.clone());
		queue.put((Message) msg1.clone());
		messageStorage.addMessageToStorage((Message) msg1.clone());
		queue.put(POISON);		

		consumer.run();
	}

	@After
	public void tearDown() {
		queue = null;
		messageStorage = null;
		consumer = null;
		msg1 = null;
		msg2 = null;
		msg3 = null;
		msg4 = null;
	}

}