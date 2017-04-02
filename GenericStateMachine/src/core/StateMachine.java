package core;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StateMachine {
	public MessageQueue itsMsgQ;
	SMReception curReception;
	State curState;
	Transition activeTransition;
	
	public static Logger LOGGER; 
		
	public StateMachine(){
		itsMsgQ = new MessageQueue(10);
		
		LOGGER = Logger.getLogger(this.getClass().getName() + " LOGGER");
		FileHandler fh;
		try {
			fh = new FileHandler(new String(this.getClass().getName()+".xml"));
			LOGGER.addHandler(fh);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOGGER.setLevel(Level.ALL); // Request that every detail gets logged.
		LOGGER.info("State Machine " + this.getClass().getName() + " just started");
	}
	
	public void setInitState(State s){
		curState =s;
	}
	
public void execute(){
	while(curState != null){		// FINAL_STATE is the state machine's final state. null is used to indicate the FINAL_STATE
		LOGGER.info("current state = " + curState.getClass().getName() + "\n");
		curReception = itsMsgQ.getNext();
		LOGGER.info("Reception received = " + curReception.toString());
		activeTransition =curState.getActiveTransition(curReception);
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
