package SimpleSiloTest;

import core.State;
import core.SMReception;
import core.Transition;

public class Filling2FullTrans extends Transition {

	public Filling2FullTrans(State ts) {
		super(ts);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean trigger(SMReception smr) {
		// TODO Auto-generated method stub
		return (smr == SimpleSiloSMEvent.HIGH_LEVEL_REACHED);
	}

	@Override
	protected void effect() {
		// TODO Auto-generated method stub
		System.out.println("sends open TO inValve");
	}

}
