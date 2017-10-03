package uml4iot.GenericStateMachine.SimpleSiloTest;

import uml4iot.GenericStateMachine.core.SMReception;
import uml4iot.GenericStateMachine.core.State;
import uml4iot.GenericStateMachine.core.StateMachine;
import uml4iot.GenericStateMachine.core.Transition;

public class SimpleSiloCtr extends StateMachine{
	State empty, filling, full, emptying;
	Transition e2ft, f2ft, f2et,e2et;
	
	public SimpleSiloCtr(){
		super(null);
		
		empty = new Empty();
		filling = new Filling();
		full = new Full();
		emptying = new Emptying();
		e2ft = new Empty2FillingTrans(empty, filling);
		f2ft = new Filling2FullTrans(filling, full);
		f2et = new Full2EmptyingTrans(full, emptying);
		e2et = new Emptying2EmptyTrans(emptying, empty);
		this.setInitState(empty);
		
	}

// state definitions
	private class Empty extends State {
		@Override
		protected void entry() {}
		@Override
		protected void doActivity() {
			System.out.println("Empty doActivity");
		}
		@Override
		protected void exit() {	}
	}

	private class Filling extends State {
		@Override
		protected void entry() {}
		@Override
		protected void doActivity() {
			System.out.println("Filling doActivity");
		}
		@Override
		protected void exit() {	}
	}

	private class Full extends State {
		@Override
		protected void entry() {}
		@Override
		protected void doActivity() {
			System.out.println("Full doActivity");
		}
		@Override
		protected void exit() {	}
	}
	
	private class Emptying extends State {
		@Override
		protected void entry() {}
		@Override
		protected void doActivity() {
			System.out.println("Emptying doActivity");
		}
		@Override
		protected void exit() {	}
	}
	
// transition definitions
	private class Empty2FillingTrans extends Transition {

	public Empty2FillingTrans(State fromState, State toState) {
		super(fromState, toState);
	}

	@Override
		protected boolean trigger(SMReception smr) {
			return (smr == SimpleSiloSMEvent.FILL);
		}
		@Override
		protected void effect() {
			System.out.println("sends open TO inValve");
		}
	}
	
	private class Filling2FullTrans extends Transition {

		public Filling2FullTrans(State fromState, State toState) {
			super(fromState, toState);
		}

		@Override
		protected boolean trigger(SMReception smr) {
			return ((smr == SimpleSiloSMEvent.HIGH_LEVEL_REACHED) || (smr == SimpleSiloSMEvent.STOP_FILLING));
		}
		@Override
		protected void effect() {
			System.out.println("sends close TO inValve");
		}
	}
	
	private class Full2EmptyingTrans extends Transition {

		public Full2EmptyingTrans(State fromState, State toState) {
			super(fromState, toState);
		}

		@Override
		protected boolean trigger(SMReception smr) {
			return (smr == SimpleSiloSMEvent.EMPTY);
		}
		@Override
		protected void effect() {
			System.out.println("sends open TO outValve");
		}
	}
	
	private class Emptying2EmptyTrans extends Transition {

		public Emptying2EmptyTrans(State fromState, State toState) {
			super(fromState, toState);
		}

		@Override
		protected boolean trigger(SMReception smr) {
			return ((smr == SimpleSiloSMEvent.LOW_LEVEL_REACHED) || (smr == SimpleSiloSMEvent.STOP_EMPTYING));
		}
		@Override
		protected void effect() {
			System.out.println("sends close TO outValve");
		}
	}
	
	
}
