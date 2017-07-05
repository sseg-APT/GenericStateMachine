package uml4iot.GenericStateMachine.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;


public abstract class BaseState<T extends SMReception> {
	private List<BaseTransition> ourTrans;
	private Collection<T> ourDefEvents;

	abstract protected void entry();
	abstract protected void doActivity();
	abstract protected void exit();

	public BaseState(){
		this.ourTrans = new ArrayList<>(10);
		this.ourDefEvents = new HashSet<>();
	}

	public void addTransition(BaseTransition t) {
		ourTrans.add(t);
	}

	protected boolean hasCompletionTrans(){
		for (BaseTransition trans : ourTrans) {
			if (trans.isCompletion()){
				return true;
			}
		}
		return false;
	}

	public void addDeferredEvent(T event){
		this.ourDefEvents.add(event);
	}

	public Collection<T> getDeferredEvents(){
		return ourDefEvents;
	}


	public BaseTransition getActiveTransition(SMReception curEvent) {
		//If null check for completion transition
		if (curEvent == null) {
			return getCompletionTransition();
		}
		//Check if event fires a transition
		for (BaseTransition trans : ourTrans) {
			if (trans.trigger(curEvent)) {
				return trans;
			}
		}
		return null;
	}

	private BaseTransition getCompletionTransition(){
		for (BaseTransition trans : ourTrans) {
			if (trans.isCompletion()){
				return trans;
			}
		}
		return null;
	}

}
