package uml4iot.GenericStateMachine.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;


/**
 * This class defines a single state in a state machine
 * and its operations
 */
public abstract class State {

    private List<Transition> ourTrans;

    private Collection<SMReception> ourDefEvents;

    public State() {
        this.ourTrans = new ArrayList<>(10);
        this.ourDefEvents = new HashSet<>();
    }

    /**
     * This method is called when the state machine is entering this state
     */
    abstract protected void entry();

    /**
     * This method is called after the entry state. Use this method to implement
     * the main work of a state
     */
    abstract protected void doActivity();

    /**
     * This method is called when the state machine is exits this state
     */
    abstract protected void exit();

    /**
     * Use this method to add a transition to this state
     *
     * @param t The transition to be added
     */
    public void addTransition(Transition t) {
        ourTrans.add(t);
    }


    /**
     * This method check if there is a transition that is on completion, meaning that
     * it does not need an event to trigger
     *
     * @return True if there is a completion transition, false if there is not
     */
    protected boolean hasCompletionTrans() {
        for (Transition trans : ourTrans) {
            if (trans.isCompletion()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add deferred events for this transition. Deferred events are events that are ignored for
     * this state but don't get discarded from the queue
     *
     * @param event the event to be added to the deferred event list
     */
    public void addDeferredEvent(SMReception event) {
        this.ourDefEvents.add(event);
    }

    /**
     * Return a list with all the deferred events for this state
     *
     * @return A collection with events
     */
    public Collection<SMReception> getDeferredEvents() {
        return ourDefEvents;
    }

    /**
     * Checks if a transition is triggered
     *
     * @param curEvent the event to check if triggers a transition
     * @return A transition that is triggered by the curEvent, null if no transition is triggered
     */
    public Transition getActiveTransition(SMReception curEvent) {
        //If null check for completion transition
        if (curEvent == null) {
            return getCompletionTransition();
        }
        //Check if event fires a transition
        for (Transition trans : ourTrans) {
            if (trans.trigger(curEvent)) {
                return trans;
            }
        }
        return null;
    }

    /**
     * Get a completion transition if there is one
     *
     * @return A transition that is on completion if there is one, null if not
     */
    private Transition getCompletionTransition() {
        for (Transition trans : ourTrans) {
            if (trans.isCompletion()) {
                return trans;
            }
        }
        return null;
    }

}
