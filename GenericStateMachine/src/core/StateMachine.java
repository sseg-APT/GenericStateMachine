package core;

public class StateMachine {
	MessageQueue itsMsgQ;
	Event curEvent;
	
	public void execute(){
		while(curState != FINAL_STATE)		// FINAL_STATE is the state machine's final state
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
			activeTrasnition.effect();
			curState=activeTrasnition.itsTargetState;
			curState.entry();
			checkDeferredEvents();		//  returns a valid for the state deffered event for further processing
			do();	// to be defined later
			
		}
			
		
	}
}
