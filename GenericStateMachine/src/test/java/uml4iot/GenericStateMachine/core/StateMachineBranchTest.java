package uml4iot.GenericStateMachine.core;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;



public class StateMachineBranchTest {

  TestStateMachine stateMachine;
  Thread stateThread;

  @Before
  public void initialize() {
    stateMachine = new TestStateMachine();
    stateThread = new Thread(stateMachine);
    stateThread.start();
  }

  @Test
  public void testStatesTransitioning() throws InterruptedException {
    Thread.sleep(100);

    assertThat(stateMachine.curState, instanceOf(TestStateMachine.State1.class));
    assertEquals(false, stateMachine.forkActive);

    stateMachine.itsMsgQ.add(TestEvents.EVENT1);
    Thread.sleep(100);

    assertThat(stateMachine.curState, instanceOf(TestStateMachine.State2.class));
    assertEquals(true, stateMachine.forkActive);
    assertThat(stateMachine.branch.curState, instanceOf(TestStateMachine.State2Branch.class));

    stateMachine.itsMsgQ.add(TestEvents.EVENT2);
    Thread.sleep(100);

    assertThat(stateMachine.curState, instanceOf(TestStateMachine.State3.class));
    assertThat(stateMachine.branch.curState, instanceOf(TestStateMachine.State3Branch.class));

    stateMachine.itsMsgQ.add(TestEvents.EVENT3);
    Thread.sleep(100);

    assertThat(stateMachine.curState, instanceOf(TestStateMachine.State3.class));
    assertEquals(null, stateMachine.branch.curState);

    stateMachine.itsMsgQ.add(TestEvents.EVENT4);
    Thread.sleep(100);

    assertThat(stateMachine.curState, instanceOf(TestStateMachine.State4.class));
    assertEquals(false, stateMachine.forkActive);

    stateMachine.itsMsgQ.add(TestEvents.EVENT5);
    Thread.sleep(100);

    assertEquals(stateThread.getState(), Thread.State.TERMINATED);

  }


  enum TestEvents implements SMReception {
    EVENT1, EVENT2, EVENT3, EVENT4, EVENT5, EVENT6
  }

  class TestStateMachine extends StateMachine {

    public TestStateMachine() {
      super(new MessageQueue());
      State state1 = new State1();
      State state2 = new State2();
      State state2Branch = new State2Branch();
      State state3 = new State3();
      State state3Branch = new State3Branch();
      State state4 = new State4();

      Transition branchTrans = new Transition1to2(state1, state2);
      branchTrans.setBranchInitState(state2Branch);

      new Transition2to3(state2, state3);
      new Transition2to3Branch(state2Branch, state3Branch);

      new Transition3to4Join(state3, state4);
      new Transition3ToEndBranch(state3Branch, null);

      new Transition4ToEnd(state4, null);


      setInitState(state1);
    }


    public class State1 extends State {

      @Override
      protected void entry() {

      }

      @Override
      protected void doActivity() {
      }

      @Override
      protected void exit() {

      }
    }

    public class State2 extends State {

      @Override
      protected void entry() {

      }

      @Override
      protected void doActivity() {

      }

      @Override
      protected void exit() {

      }
    }

    public class State3 extends State {

      @Override
      protected void entry() {

      }

      @Override
      protected void doActivity() {
      }

      @Override
      protected void exit() {

      }
    }

    public class State2Branch extends State {

      @Override
      protected void entry() {

      }

      @Override
      protected void doActivity() {
      }

      @Override
      protected void exit() {

      }
    }

    public class State3Branch extends State {

      @Override
      protected void entry() {

      }

      @Override
      protected void doActivity() {
      }

      @Override
      protected void exit() {

      }
    }

    public class State4 extends State {

      @Override
      protected void entry() {

      }

      @Override
      protected void doActivity() {
      }

      @Override
      protected void exit() {

      }
    }


    public class Transition1to2 extends Transition {


      public Transition1to2(State fromState, State toState) {
        super(fromState, toState, true, false, false);
      }

      @Override
      protected boolean trigger(SMReception smr) {
        return smr.equals(TestEvents.EVENT1);
      }

      @Override
      protected void effect() {

      }
    }


    public class Transition2to3 extends Transition {

      public Transition2to3(State fromState, State toState) {
        super(fromState, toState);
      }

      @Override
      protected boolean trigger(SMReception smr) {
        return smr.equals(TestEvents.EVENT2);
      }

      @Override
      protected void effect() {

      }
    }

    public class Transition2to3Branch extends Transition {

      public Transition2to3Branch(State fromState, State toState) {
        super(fromState, toState);
      }

      @Override
      protected boolean trigger(SMReception smr) {
        return smr.equals(TestEvents.EVENT2);
      }

      @Override
      protected void effect() {

      }
    }

    public class Transition3ToEndBranch extends Transition {

      public Transition3ToEndBranch(State fromState, State toState) {
        super(fromState, toState);
      }

      @Override
      protected boolean trigger(SMReception smr) {
        return smr.equals(TestEvents.EVENT3);
      }

      @Override
      protected void effect() {
      }
    }

    public class Transition3to4Join extends Transition {


      public Transition3to4Join(State fromState, State toState) {
        super(fromState, toState, false, true, false);
      }

      @Override
      protected boolean trigger(SMReception smr) {
        return smr.equals(TestEvents.EVENT4);
      }

      @Override
      protected void effect() {

      }
    }

    public class Transition4ToEnd extends Transition {

      public Transition4ToEnd(State fromState, State toState) {
        super(fromState, toState);
      }

      @Override
      protected boolean trigger(SMReception smr) {
        return smr.equals(TestEvents.EVENT5);
      }

      @Override
      protected void effect() {
      }
    }




  }
}
