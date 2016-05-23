package com.container;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 使用阻塞队列实现生产者消费者
 * 
 * @author wangyc
 * 
 */
public class ProducerConsumerTest {
	private int queueSize = 10;
	private ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(queueSize);

	public static void main(String[] args) {
		ProducerConsumerTest producerConsumerTest = new ProducerConsumerTest();
		Producer producer = producerConsumerTest.new Producer();
		Consumer consumer = producerConsumerTest.new Consumer();
		producer.start();
		consumer.start();
	}

	class Producer extends Thread {
		public void run() {
			produce();
		}

		private void produce() {
			while (true) {
				try {
					queue.put(1);
					System.out.println("向队列取中插入一个元素，队列剩余空间：" + (queueSize - queue.size()));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	class Consumer extends Thread {
		public void run() {
			consume();
		}

		private void consume() {
			while (true) {
				try {
					queue.take();
					System.out.println("从队列取走一个元素，队列剩余" + queue.size() + "个元素");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
