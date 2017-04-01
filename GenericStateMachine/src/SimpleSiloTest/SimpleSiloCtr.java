package SimpleSiloTest;

import core.State;
import core.StateMachine;
import core.Transition;

public class SimpleSiloCtr extends StateMachine{
	State empty, filling, full, emptying;
	Transition e2ft, f2ft, f2et,e2et;
	
	public SimpleSiloCtr(){
		super();
		
		empty = new Empty();
		filling = new Filling();
		full = new Full();
		emptying = new Emptying();
		e2ft = new Empty2FillingTrans(filling);
		f2ft = new Filling2FullTrans(full);
		f2et = new Full2EmptyingTrans(emptying);
		e2et = new Emptying2EmptyTrans(empty);
		empty.addTransition(e2ft);
		filling.addTransition(f2ft);
		full.addTransition(f2et);
		emptying.addTransition(e2et);
		this.setInitState(empty);
		
	}
	
	
}
