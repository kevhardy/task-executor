package edu.utdallas.taskExecutorImpl;

import edu.utdallas.taskExecutor.Task;
import edu.utdallas.taskExecutor.TaskExecutor;

public class TaskExecutorImpl implements TaskExecutor
{
	private TaskRunner runnerPool[];
	private BlockingQueue blockingQueue;
	
	public TaskExecutorImpl(int numThreads) {
		
		this.blockingQueue = new BlockingQueue(100);
		this.runnerPool = new TaskRunner[numThreads];
		
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
			blockingQueue.put(task);
		} 
		catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

}
