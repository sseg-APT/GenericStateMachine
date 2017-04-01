package SimpleSiloTest;

import core.State;
import core.StateMachineReception;
import core.Transition;

public class Full2EmptyingTrans extends Transition {

	public Full2EmptyingTrans(State ts) {
		super(ts);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean trigger(StateMachineReception smr) {
		// TODO Auto-generated method stub
		return (smr == SimpleSiloSMEvent.EMPTY);
	}

	@Override
	protected void effect() {
		// TODO Auto-generated method stub
		System.out.println("sends open TO inValve");
	}

}
