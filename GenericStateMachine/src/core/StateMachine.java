package core;

public class StateMachine {
	public MessageQueue itsMsgQ;
	StateMachineReception curEvent;
	State curState;
	Transition activeTransition;
		
	public StateMachine(){
		itsMsgQ = new MessageQueue(10);
	}
	
	public void setInitState(State s){
		curState =s;
	}
	
public void execute(){
	while(curState != null){		// FINAL_STATE is the state machine's final state. null is used to indicate the FINAL_STATE
		curEvent = itsMsgQ.getNext();
		activeTransition =curState.getActiveTransition(curEvent);
		if(activeTransition==null){
			System.out.println("No acive transition");
			/*if(curState.defEventPool.isDeffered(curEvent)){
				defEventsQ.putEvent(curEvent);
			}
			else{
				//LOGGER(ReportSEVERE ERROR);
			}*/
		} 
		else{	// fire transition
			//curState.doActivity.terminate();		// to be defined later
			curState.exit();
			activeTransition.effect();
			curState=activeTransition.itsTargetState;
			if(curState!=null){			// if curState not Final state
				curState.entry();
				/*  just for test
				 if(getValidDeferredEvent()!=null){ // gets a valid for the current state event from the deferred Pool, if any 
					//  returns a valid for the current state deferred event for further processing
				}
				*/
				curState.doActivity();	// to be defined later. Probably a Thread will be activated having as run the do method
				if(curState.hasCompletionTrans()){		// a Completion transition is one without a trigger
					// proceed to fire the transition 
				}
				
			}
			
		}
	}		
		
}
}
