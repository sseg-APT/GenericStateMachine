package core;

public abstract class Transition {
	protected State itsTargetState;
	
	public Transition(State ts){
		itsTargetState = ts;
	}
		
	abstract protected boolean trigger(SMReception smr);
	abstract protected void effect();
}
