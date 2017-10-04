package uml4iot.GenericStateMachine.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingDeque;


public class MessageQueue extends LinkedBlockingDeque<SMReception> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<MessageQueue> childList;

	public MessageQueue(){
		childList = new ArrayList<>(10);
	}

	@Override
	public boolean add(SMReception e) {
		forwardToChilds(e);
		return super.add(e);
	}


	public SMReception getNext() throws InterruptedException{
		// TODO Auto-generated method stub
		SMReception ev=null;
		ev=this.take();
		return ev;
	}

	public SMReception getNext(Collection<SMReception> ignoreSet) throws InterruptedException{
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

	public void addChildQueue(MessageQueue queue){
		childList.add(queue);
	}

	public void removeChildQueue(MessageQueue queue){
		childList.remove(queue);
	}

	private void forwardToChilds(SMReception message){
		for (MessageQueue child : childList) {
			child.add(message);
		}
	}
	
}
