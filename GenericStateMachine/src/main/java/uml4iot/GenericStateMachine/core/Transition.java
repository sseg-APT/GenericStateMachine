package uml4iot.GenericStateMachine.core;


/**
 * Represents a transition of a state, that is triggered to go to the next state.
 * Transitions are added to states and check for events that triggers them
 */
public abstract class Transition {

    protected State itsTargetState;

    protected State branchInitState;

    private boolean fork = false;

    private boolean join = false;

    private boolean completion = false;

    /**
     * Complex constructor of the transition.
     *
     * @param fromState  The source state
     * @param toState    The target state
     * @param fork       True if the transition forks the state machine to have multiple target
     *                   states
     * @param join       True if there is another branch that must be completed before this
     *                   transition completes
     * @param completion True if the transition is completion, ie it does'nt require an event to
     *                   trigger
     */
    public Transition(State fromState, State toState, boolean fork, boolean join, boolean completion) {
        fromState.addTransition(this);
        itsTargetState = toState;
        this.fork = fork;
        this.join = join;
        this.completion = completion;
    }

    /**
     * Simpler constructor of the transition, only source and target states are required
     *
     * @param fromState The source state
     * @param toState   The target state
     */
    public Transition(State fromState, State toState) {
        this(fromState, toState, false, false, false);
    }

    /**
     * Builder method to activate completion.
     * When this is called, transition will not require an event to trigger
     *
     * @return the current instance
     */
    public Transition setCompletion() {
        this.completion = true;
        return this;
    }

    /**
     * Builder method to activate fork
     * When this is called, transition will enable forking,
     * ie 2 states will be activated in parallel.
     * If fork is enabled, {@link Transition#branchInitState} must be called
     * with the appropriate init state (branch init state)
     *
     * @return the current instance
     */
    public Transition setFork() {
        this.fork = true;
        return this;
    }

    /**
     * Builder method to enable join
     * When this is called, transition will wait for forks to complete
     * before continuing to the next state
     *
     * @return the current instance
     */
    public Transition setJoin() {
        this.join = true;
        return this;
    }

    /**
     * Getter method for checking if transition has fork enabled
     *
     * @return true if transition has fork enabled else false
     */
    public boolean hasFork() {
        return fork;
    }

    /**
     * Getter method for checking if transition has join enabled
     *
     * @return true if transition has join enabled, else false
     */
    public boolean hasJoin() {
        return join;
    }

    /**
     * If fork is enabled (by {@link Transition#setFork()}) this method must be called
     * with the branch init state
     */
    public void setBranchInitState(State st) {
        branchInitState = st;
    }

    /**
     * Getter method for checking if transition has completion enabled
     *
     * @return true if completion is enabled, else false
     */
    public boolean isCompletion() {
        return completion;
    }

    /**
     * This method is called from the state machine to check if the transition is
     * trigger by an event.
     *
     * @param smr current event for checking if this transition triggers
     * @return true if the event trigger change of state, else false
     */
    abstract protected boolean trigger(SMReception smr);

    /**
     * A hook to add functionality when a transition is triggered.
     * It is called after the {@link State#exit()} method
     */
    abstract protected void effect();
}
