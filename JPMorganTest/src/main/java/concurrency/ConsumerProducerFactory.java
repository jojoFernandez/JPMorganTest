package concurrency;

import gateway.GenericGateway;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.configuration.XMLConfiguration;
import dataSructures.MessageStorage;
import message.Message;

public class ConsumerProducerFactory {

	private int numberOFProducers;
	private int numberOFConsumers;

	public ConsumerProducerFactory(int numberOFProducers, int numberOFConsumers) {
		this.numberOFProducers = numberOFProducers;
		this.numberOFConsumers = numberOFConsumers;
	}

	public ExecutorService provideProducerExecutor() throws IllegalArgumentException{
		return Executors.newFixedThreadPool(numberOFProducers);
	}

	public Runnable provideProducer(BlockingQueue<Message> queue, MessageStorage messageStorage, XMLConfiguration appConfig) {
		return new Producer(queue, messageStorage, appConfig);
	}

	public ExecutorService provideConsumerExecutor() {
		return Executors.newFixedThreadPool(numberOFConsumers);
	}

	public Runnable provideConsumer(BlockingQueue<Message> queue, MessageStorage messageStorage, GenericGateway genericGateway) {
		return new Consumer(queue, messageStorage, genericGateway);
	}

}