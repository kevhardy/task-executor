package edu.utdallas.taskExecutorImpl;

public class TaskRunner implements Runnable {
	
	private BlockingQueue queue;

	public TaskRunner() {
		// Empty default constructor (Not needed but preferred over implicit)
	}
	
	// Sets the task runner's queue it retrieves tasks from
	public void setBlockingQueue(BlockingQueue queue) {
		
		this.queue = queue;
	}
	
	@Override
	public void run() {
		
		// Will forever try to receive tasks from the queue unless blocked
		while(true) {
			try {
				queue.take().execute(); // take() blocks if queue is empty
			}
			catch(Exception e) {
				System.out.println(e);
				e.printStackTrace();
				System.exit(0);
			}
		}
		
	}

}
