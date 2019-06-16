package edu.utdallas.taskExecutorImpl;

import edu.utdallas.taskExecutor.Task;

public class TaskRunner implements Runnable {
	
	private BlockingQueue queue;

	public TaskRunner() {
		
	}
	
	public void setBlockingQueue(BlockingQueue queue) {
		
		this.queue = queue;
	}
	
	@Override
	public void run() {
		
		while(true) {
			try {
				Task newTask = queue.take();
				newTask.execute();
			}
			catch(Exception e) {
				System.out.println(e);
				e.printStackTrace();
				System.exit(0);
			}
		}
		
	}

}
