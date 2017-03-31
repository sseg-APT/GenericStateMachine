package core;

public class StateMachine {
	MessageQueue itsMsgQ;
	Event curEvent;
	State curState;
	Transition activeTransition;
		
public void execute(){
	while(curState != FINAL_STATE){		// FINAL_STATE is the state machine's final state
		curEvent = itsMsgQ.getNext();
		if(activeTrasnition =(curState.getActiveTransition(curEvent))==null){
			if(curState.defEventPool.isDeffered(curEvent)){
				defEventsQ.putEvent(curEvent);
			}
			else{
				//LOGGER(ReportSEVERE ERROR);
			}
		} 
		else{	// fire transition
			curState.exit();
			//curState.doActivity.terminate();		// to be defined later
			activeTrasnition.effect();
			curState=activeTrasnition.itsTargetState;
			if(curState!=FINAL_STATE){
				curState.entry();
				if(getValidDeferredEvent()!=null){ // gets a valid for the current state event from the deferred Pool, if any 
					//  returns a valid for the current state deferred event for further processing
				}
				do();	// to be defined later
				if(curState.hasCompletionTransition()){		// a Completion transition is one without a trigger
					// proceed to fire the transition 
				}
				
			}
			
		}
	}		
		
}
}
