package edu.utdallas.taskExecutorImpl;

import edu.utdallas.taskExecutor.Task;
import edu.utdallas.taskExecutor.TaskExecutor;

public class TaskExecutorImpl implements TaskExecutor
{
	private TaskRunner runnerPool[]; // Pool of TaskRunners used to execute tasks
	private BlockingQueue blockingQueue; // FIFO queue that is thread-safe and blocking
	
	public TaskExecutorImpl(int numThreads) {
		
		this.blockingQueue = new BlockingQueue(100);
		this.runnerPool = new TaskRunner[numThreads];
		
		// Initializes the pool of threads and starts them
		for (int i=0; i<numThreads; i++) {
			runnerPool[i] = new TaskRunner();
			runnerPool[i].setBlockingQueue(this.blockingQueue);
			
			Thread newThread = new Thread(runnerPool[i]);
			newThread.start();
		}
	}

	@Override
	public void addTask(Task task) {
		
		try {
			blockingQueue.put(task); // Will block if queue is full
		} 
		catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

}
