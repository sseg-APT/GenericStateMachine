package uml4iot.GenericStateMachine.core;

public abstract class Transition {
	protected State itsTargetState;
	private boolean fork=false;
	private boolean join = false;
	protected State branchInitState;
	
	public Transition(State ts, boolean fork, boolean join){
		itsTargetState = ts;
		this.fork = fork;
		this.join = join;
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
	abstract protected boolean trigger(SMReception smr);
	abstract protected void effect();
}
