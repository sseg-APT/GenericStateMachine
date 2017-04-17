//support for one branch at a time added. 
//Transition that leads to fork should be characterized with fork=true and the branche's initState should be defined using setBranchInitState(). 
//Transition to the initial state of branch should not be defined. 
//Only the transition of main is characterized with join=true. 
//the branch transition to join should be assigned null as its target state
//Tester is HeatedSiloTest package (see state chart)

package uml4iot.GenericStateMachine.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StateMachine implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(StateMachine.class);
    public MessageQueue itsMsgQ;
    State initState;
    SMReception curReception;
    State curState = null;
    boolean eventDiscarded = false;
    Transition activeTransition;
    // fork impl
    public MessageQueue branchMsgQ = null;

    boolean forkActive = false;
    Thread itsBranchThread = null;
    StateMachine branch;


    public StateMachine(MessageQueue msgQ) {
        if (msgQ != null) itsMsgQ = msgQ;        //for branch SMs
        else
            itsMsgQ = new MessageQueue();
    }


    public void setInitState(State s) {
        initState = s;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        curState = initState;
        while (curState != null) {        // FINAL_STATE is the state machine's final state. null is used to indicate the FINAL_STATE
            if (!eventDiscarded) {
                curState.entry();
                curState.doActivity();    // to be defined later. Probably a Thread will be activated having as run the do method
                LOG.info("current state = " + curState.getClass().getName() + "\n");
            }
            if (!curState.hasCompletionTrans()) {        // a Completion transition is one without a trigger.
                // deactivated waiting for deferred event implementation
                // if(getValidDeferredEvent()!=null){ // gets a valid for the current state event from the deferred Pool, if any
                //  returns a valid for the current state deferred event for further processing
                //} else {
                curReception = itsMsgQ.getNext(curState.getDeferredEvents());
                LOG.info("Reception received = " + curReception.toString());
                //}
            } else
                curReception = null;        // is used to activate the on completion transition

            activeTransition = curState.getActiveTransition(curReception);

            if (activeTransition == null) {
                LOG.info("No acive transition for reception " + curReception.toString() + " at main branch\n");
                if (false) {    // check if reception is deferred and if yes keep it
                /*if(curState.defEventPool.isDeffered(curEvent)){
					defEventsQ.putEvent(curEvent);
					}*/
                } else if (forkActive) {        //  forward event to branch if active
                    branchMsgQ.add(curReception);
                    LOG.info("Reception " + curReception.toString() + " dispatched to branch\n");
                } else {
                    LOG.error("Reception " + curReception.toString() + " is not handled at state " + curState);
                }
                eventDiscarded = true;
            } else {    // fire transition
                //curState.doActivity.terminate();		// to be defined later
                if (activeTransition.hasFork()) {
                    StateMachine bsm;
                    forkActive = true;
                    branchMsgQ = new MessageQueue();
                    bsm = new StateMachine(branchMsgQ);
                    bsm.setInitState(activeTransition.branchInitState);
                    itsBranchThread = new Thread(bsm);
                    itsBranchThread.setName(Thread.currentThread().getName() + "-branchSmT");
                    itsBranchThread.start();
                }

                curState.exit();
                activeTransition.effect();
                if (activeTransition.hasJoin()) {
                    try {
                        (itsBranchThread).join();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    forkActive = false;
                }
                curState = activeTransition.itsTargetState;
                eventDiscarded = false;
            }
        }
        LOG.info("State Machine " + Thread.currentThread().getName() + " terminated");

    }

}
