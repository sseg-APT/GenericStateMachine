package SimpleSiloTest;

import core.State;
import core.SMReception;
import core.Transition;

public class Empty2FillingTrans extends Transition {

	public Empty2FillingTrans(State filling) {
		// TODO Auto-generated constructor stub
		super(filling);
	}

	@Override
	protected boolean trigger(SMReception smr) {
		// TODO Auto-generated method stub
		return (smr == SimpleSiloSMEvent.FILL);
	}

	@Override
	protected void effect() {
		// TODO Auto-generated method stub
		System.out.println("sends open TO inValve");
	}

}
