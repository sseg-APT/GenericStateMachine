package core;

import java.util.ArrayList;
import java.util.List;


public abstract class State {
	private List<Transition> ourTrans;
		
	abstract protected void entry();
	abstract protected void doActivity();
	abstract protected void exit();

	public State(){
		this.ourTrans = new ArrayList<>(10);
	}

	public void addTransition(Transition t) {
		ourTrans.add(t);
	}

	protected boolean hasCompletionTrans(){
		boolean hct=false;
		
		return hct;
	}
	
	protected Event  getValidDeferredEvent(){
		Event ev=null;
		
		return ev;
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
