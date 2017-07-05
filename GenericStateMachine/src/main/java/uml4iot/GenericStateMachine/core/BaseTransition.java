package uml4iot.GenericStateMachine.core;

public abstract class BaseTransition<T extends SMReception> {

	protected BaseState itsTargetState;
	private boolean fork=false;
	private boolean join = false;
	private boolean completion = false;
	protected BaseState branchInitState;
	
	public BaseTransition(BaseState fromState, BaseState toState, boolean fork, boolean join, boolean completion){
		fromState.addTransition(this);
		itsTargetState = toState;
		this.fork = fork;
		this.join = join;
		this.completion = completion;
	}

	public BaseTransition(BaseState fromState, BaseState toState){
		this(fromState, toState, false, false, false);
	}

	public BaseTransition setCompletion() {
		this.completion = true;
		return this;
	}

	public BaseTransition setFork() {
		this.fork = true;
		return this;
	}

	public BaseTransition setJoin() {
		this.join = true;
		return this;
	}

	public boolean hasFork(){
		return fork;
	}
	public boolean hasJoin(){
		return join;
	}
	
	public void setBranchInitState(BaseState st){
		branchInitState = st;
	}

	public boolean isCompletion(){
		return completion;
	}

	abstract protected boolean trigger(T smr);
	abstract protected void effect();
}
