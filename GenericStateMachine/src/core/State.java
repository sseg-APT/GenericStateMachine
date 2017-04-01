package core;

import java.util.concurrent.ArrayBlockingQueue;

import plantController.SiloCtrlEvent;

public abstract class State {
	private Transition outTrans;
		
	abstract protected void entry();
	abstract protected void doActivity();
	abstract protected void exit();
	
	public void addTransition(Transition t){
		outTrans = t;
	}
	protected boolean hasCompletionTrans(){
		boolean hct=false;
		
		return hct;
	}
	
	protected Event  getValidDeferredEvent(){
		Event ev=null;
		
		return ev;
	}
	public Transition getActiveTransition(StateMachineReception curEvent) {		// created for testing. Valid just for one trans per state. to be deleted
		// TODO Auto-generated method stub
		return outTrans.trigger(curEvent)? outTrans: null;
	}
	
}
