package core;

import java.util.concurrent.ArrayBlockingQueue;



public class MessageQueue extends ArrayBlockingQueue<SMReception>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MessageQueue(int n){
		super(n);
	}

	public SMReception getNext() {
		// TODO Auto-generated method stub
		SMReception ev=null;
		try {
			ev=this.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ev;
	}
	
}
