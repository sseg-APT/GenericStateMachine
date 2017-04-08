package core;

import java.util.Collection;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingDeque;


public class MessageQueue extends LinkedBlockingDeque<SMReception> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


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

	public SMReception getNext(Collection<SMReception> ignoreSet){
		Stack<SMReception> ignored = new Stack<>();
		SMReception ev = null;
		//Get events until one is not in the ignoreSet
		while (ev == null){
			ev = getNext();
			if (ignoreSet.contains(ev)){
				ignored.push(ev);
				ev = null;
			}
		}
		//Add ignored events to the queue again
		while (!ignored.isEmpty()){
			this.addFirst(ignored.pop());
		}

		return ev;
	}
	
}
