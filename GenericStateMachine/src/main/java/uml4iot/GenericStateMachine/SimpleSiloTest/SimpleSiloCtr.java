package uml4iot.GenericStateMachine.SimpleSiloTest;

import uml4iot.GenericStateMachine.core.SMReception;
import uml4iot.GenericStateMachine.core.BaseState;
import uml4iot.GenericStateMachine.core.StateMachine;
import uml4iot.GenericStateMachine.core.BaseTransition;

public class SimpleSiloCtr extends StateMachine{
	BaseState empty, filling, full, emptying;
	BaseTransition e2ft, f2ft, f2et,e2et;
	
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
	private class Empty extends BaseState {
		@Override
		protected void entry() {}
		@Override
		protected void doActivity() {
			System.out.println("Empty doActivity");
		}
		@Override
		protected void exit() {	}
	}

	private class Filling extends BaseState {
		@Override
		protected void entry() {}
		@Override
		protected void doActivity() {
			System.out.println("Filling doActivity");
		}
		@Override
		protected void exit() {	}
	}

	private class Full extends BaseState {
		@Override
		protected void entry() {}
		@Override
		protected void doActivity() {
			System.out.println("Full doActivity");
		}
		@Override
		protected void exit() {	}
	}
	
	private class Emptying extends BaseState {
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
	private class Empty2FillingTrans extends BaseTransition {

	public Empty2FillingTrans(BaseState fromState, BaseState toState) {
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
	
	private class Filling2FullTrans extends BaseTransition {

		public Filling2FullTrans(BaseState fromState, BaseState toState) {
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
	
	private class Full2EmptyingTrans extends BaseTransition {

		public Full2EmptyingTrans(BaseState fromState, BaseState toState) {
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
	
	private class Emptying2EmptyTrans extends BaseTransition {

		public Emptying2EmptyTrans(BaseState fromState, BaseState toState) {
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
