package edu.utdallas.taskExecutorImpl;

import edu.utdallas.taskExecutor.Task;

public class BlockingQueue {
	
	private Task buffer[]; // buffer of Tasks waiting to be executed
	private int nextIn, nextOut; // pointers for where next Tasks will be added/removed
	private int count;
	private Object notFull = new Object(); // Monitor lock used for notifying when the queue is not full
	private Object notEmpty = new Object(); // Monitor lock used for notifying when the queue is not empty

	public BlockingQueue(int size) {
		
		this.buffer = new Task[size];
		nextIn = nextOut = count = 0;
	}
	
	public void put(Task task) throws Exception {

		// Thread safe condition check for buffer's size
		// Will block if queue is currently full
		synchronized (notFull) {
			while (count >= buffer.length) {
				notFull.wait();
			}
		}
		
		// Inserts task into the queue and notifies notEmpty monitor
		synchronized (notEmpty) {
			try {
				buffer[nextIn] = task;
				nextIn = (nextIn + 1) % buffer.length;
				count++;
			} finally {
				notEmpty.notify();
			}
		}
	}
	
	public Task take() throws Exception {

		// Thread safe condition check for buffer's size
		// Will block if queue is currently empty
		synchronized (notEmpty) {
			while (count == 0) {
				notEmpty.wait();
			}
		}

		// Removes and returns task from queue and notifies notFull monitor
		synchronized (notFull) {
			try {
				Task result = buffer[nextOut];
				nextOut = (nextOut + 1 ) % buffer.length;
				count--;
				return result;
			} finally {
				notFull.notify();
			}
		}	
	}

}
