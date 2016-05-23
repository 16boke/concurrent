package com.thread;

import java.util.PriorityQueue;
/**
 * 经典的生产者和消费者
 * @author wangyc
 *
 */
public class ProducerConsumerTest {
	private int queueSize = 10;
	private PriorityQueue<Integer> queue = new PriorityQueue<Integer>(queueSize);

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
				synchronized (queue) {
					while (queue.size() == queueSize) {
						try {
							System.out.println("队列满，等待有空余空间");
							queue.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
							queue.notify();
						}
					}
					queue.offer(1); // 每次插入一个元素
					queue.notify();
					System.out.println("向队列中插入一个元素，队列剩余空间：" + (queueSize - queue.size()));
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
				synchronized (queue) {
					while (queue.size() == 0) {
						try {
							System.out.println("队列空，等待数据");
							queue.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
							queue.notify();
						}
					}
					queue.poll(); // 每次移走队首元素
					queue.notify();
					System.out.println("从队列中取走一个元素，队列剩余" + queue.size() + "个元素");
				}
			}
		}
	}
}
