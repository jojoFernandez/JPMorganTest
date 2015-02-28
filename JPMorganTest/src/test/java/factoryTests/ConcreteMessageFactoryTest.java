package factoryTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import message.ConcreteMessageFactory;
import message.Message;
import message.MessageFactory;

public class ConcreteMessageFactoryTest {

	private MessageFactory messageFactory = null; 

	@Before
	public void setUp() throws Exception {
		messageFactory = new ConcreteMessageFactory(); 
	}

	@Test
	public void testGenerateRandomMessageUpperLimit() throws IllegalArgumentException{
		Object objMsg = messageFactory.generateRandomMessage(10);
		Message msg = null;

		if (objMsg instanceof Message) {
			msg = (Message) objMsg; 
		} else {
			fail();		
		}

		if (msg.getGroupID() < 10) {
		} else {
			fail();		
		}
	}

	@Test
	public void testCreateCancellationMessage() throws IllegalArgumentException {
		Object objMsg = messageFactory.createCancellationMessage(1);
		Message msg = null;
	
		if (objMsg instanceof Message) {
			msg = (Message) objMsg; 
		} else {
			fail();		
		}

		if (msg.getData().equalsIgnoreCase("CANCELLATION_MESSAGE")) {
		} else {
			fail();		
		}
	}

	@Test
	public void testCreateTerminationMessage() throws IllegalArgumentException {
		Object objMsg = messageFactory.createTerminationMessage(1);
		Message msg = null;
	
		if (objMsg instanceof Message) {
			msg = (Message) objMsg; 
		} else {
			fail();		
		}

		if (msg.getData().equalsIgnoreCase("TERMINATION_MESSAGE")) {
		} else {
			fail();		
		}
	}	

	@After
	public void tearDown() {
		messageFactory = null;
	}

}