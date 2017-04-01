package SimpleSiloTest;

import core.State;
import core.StateMachineReception;
import core.Transition;

public class Emptying2EmptyTrans extends Transition {

	public Emptying2EmptyTrans(State ts) {
		super(ts);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean trigger(StateMachineReception smr) {
		// TODO Auto-generated method stub
		return (smr == SimpleSiloSMEvent.LOW_LEVEL_REACHED);
	}

	@Override
	protected void effect() {
		// TODO Auto-generated method stub
		System.out.println("sends open TO inValve");
	}

}
