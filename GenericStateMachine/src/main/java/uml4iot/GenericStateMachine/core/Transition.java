package uml4iot.GenericStateMachine.core;

public abstract class Transition {
	protected State itsTargetState;
	private boolean fork=false;
	private boolean join = false;
	private boolean completion = false;
	protected State branchInitState;
	
	public Transition(State fromState, State toState, boolean fork, boolean join, boolean completion){
		fromState.addTransition(this);
		itsTargetState = toState;
		this.fork = fork;
		this.join = join;
		this.completion = completion;
	}

	public Transition(State fromState, State toState){
		this(fromState, toState, false, false, false);
	}

	public Transition setCompletion() {
		this.completion = true;
		return this;
	}

	public Transition setFork() {
		this.fork = true;
		return this;
	}

	public Transition setJoin() {
		this.join = true;
		return this;
	}

	public boolean hasFork(){
		return fork;
	}
	public boolean hasJoin(){
		return join;
	}
	
	public void setBranchInitState(State st){
		branchInitState = st;
	}

	public boolean isCompletion(){
		return completion;
	}

	abstract protected boolean trigger(SMReception smr);
	abstract protected void effect();
}
