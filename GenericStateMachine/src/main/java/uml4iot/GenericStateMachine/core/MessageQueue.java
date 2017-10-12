package uml4iot.GenericStateMachine.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingDeque;


/**
 * The message queue for the state machine. Its a LinkedBlockingDeque that
 * takes SMReception objects and some extra functionality
 */
public class MessageQueue extends LinkedBlockingDeque<SMReception> {

	private static final long serialVersionUID = 1L;
	private ArrayList<MessageQueue> childList;

	public MessageQueue(){
		childList = new ArrayList<>(10);
	}

	/**
	 * Add a new object object in the queue
	 * {@link java.util.concurrent.LinkedBlockingDeque#add(Object)}
	 * @param e the new object to be added in the queue
	 * @return return true if insert is successful
	 */
	@Override
	public boolean add(SMReception e) {
		forwardToChilds(e);
		return super.add(e);
	}

	/**
	 * Gets the next item in the queue. Blocking method
	 * @return the next item
	 * @throws InterruptedException when interrupted if blocked
	 */
	public SMReception getNext() throws InterruptedException{
		// TODO Auto-generated method stub
		SMReception ev=null;
		ev=this.take();
		return ev;
	}

	/**
	 * Gets the next item in the queue, except if it is in the ignoreSet.
	 * Like {@link #add(SMReception)} but takes an extra argument, the ignoreSet.
	 * @param ignoreSet A set of events that we don't want
	 * @return the next item
	 * @throws InterruptedException when interrupted if blocked
	 */
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

	/**
	 * Adds a child queue to copy and forward all objects that are added in this queue
	 * Useful when state machine branches
	 * @param queue A queue to be added as child
	 */
	public void addChildQueue(MessageQueue queue){
		childList.add(queue);
	}


	/**
	 * Removes a child queue that was previously added. Useful when the state machine branches
	 * merge
	 * @param queue the child queue to remove
	 */
	public void removeChildQueue(MessageQueue queue){
		childList.remove(queue);
	}


	/**
	 * Forwards a message to all child queues
	 * @param message the message to be forwarded
	 */
	private void forwardToChilds(SMReception message){
		for (MessageQueue child : childList) {
			child.add(message);
		}
	}
	
}
