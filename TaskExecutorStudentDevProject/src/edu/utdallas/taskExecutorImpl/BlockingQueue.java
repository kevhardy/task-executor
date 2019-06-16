package edu.utdallas.taskExecutorImpl;

import edu.utdallas.taskExecutor.Task;

public class BlockingQueue {
	
	private Task buffer[];
	private int nextIn, nextOut; // pointers for where next Tasks will be added/removed
	private int count;
	private Object notFull = new Object(); // Monitor lock used for notifying when the queue is not full
	private Object notEmpty = new Object(); // Monitor lock used for notifying when the queue is not empty

	public BlockingQueue(int size) {
		
		this.buffer = new Task[size];
		nextIn = nextOut = count = 0;
	}
	
	public void put(Task task) throws Exception {

		synchronized (notFull) {
			while (count >= buffer.length) {
				notFull.wait();
			}
		}
		
		synchronized (notEmpty) {
			buffer[nextIn] = task;
			nextIn = (nextIn + 1) % buffer.length;
			count++;
			notEmpty.notify();
		}
	}
	
	public Task take() throws Exception {

		synchronized (notEmpty) {
			while (count == 0) {
				notEmpty.wait();
			}
		}

		synchronized (notFull) {
			Task result = buffer[nextOut];
			nextOut = (nextOut + 1 ) % buffer.length;
			count--;
			notFull.notify();
			return result;
		}	
	}

}
