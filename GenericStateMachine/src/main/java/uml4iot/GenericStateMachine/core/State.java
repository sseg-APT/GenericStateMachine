package uml4iot.GenericStateMachine.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;


public abstract class State {
	private List<Transition> ourTrans;
	private Collection<SMReception> ourDefEvents;

	abstract protected void entry();
	abstract protected void doActivity();
	abstract protected void exit();

	public State(){
		this.ourTrans = new ArrayList<>(10);
		this.ourDefEvents = new HashSet<>();
	}

	public void addTransition(Transition t) {
		ourTrans.add(t);
	}

	protected boolean hasCompletionTrans(){
		for (Transition trans : ourTrans) {
			if (trans.isCompletion()){
				return true;
			}
		}
		return false;
	}

	public void addDeferredEvent(SMReception event){
		this.ourDefEvents.add(event);
	}

	public Collection<SMReception> getDeferredEvents(){
		return ourDefEvents;
	}


	public Transition getActiveTransition(SMReception curEvent) {
		//Check if event fires a transition
		for (Transition trans : ourTrans) {
			if (trans.trigger(curEvent)) {
				return trans;
			}
		}
		return null;
	}
	
}
