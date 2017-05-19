package uml4iot.GenericStateMachine.core;

public abstract class Transition {
	protected State itsTargetState;
	private boolean fork=false;
	private boolean join = false;
	private boolean completion = false;
	protected State branchInitState;
	
	public Transition(State ts, boolean fork, boolean join, boolean completion){
		itsTargetState = ts;
		this.fork = fork;
		this.join = join;
		this.completion = completion;
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
