package uml4iot.GenericStateMachine.core;

import org.hamcrest.core.Is;
import org.junit.*;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;



public class StateMachineTest {

  StateMachine stateMachine;
  Thread stateThread;

  @Before
  public void initialize(){
    stateMachine = new TestStateMachine();
    stateThread = new Thread(stateMachine);
    stateThread.start();
  }

  @After
  public void finalize(){
    //stateThread.interrupt();
  }


  @Test
  public void testStatesTransitioning() throws Exception {
    Thread.sleep(100);
    assertThat(stateMachine.curState, instanceOf(TestStateMachine.State1.class));
    stateMachine.itsMsgQ.add(TestEvents.EVENT1);

    Thread.sleep(100);
    assertThat(stateMachine.curState, instanceOf(TestStateMachine.State2.class));

    stateMachine.itsMsgQ.add(TestEvents.EVENT2);
    Thread.sleep(100);
    assertThat(stateMachine.curState, instanceOf(TestStateMachine.State3.class));

    stateMachine.itsMsgQ.add(TestEvents.EVENT3);
    Thread.sleep(100);
    assertEquals(stateThread.getState(), Thread.State.TERMINATED);

  }

  



  //----------------Testing State Machine--------------

  enum TestEvents implements SMReception{
    EVENT1, EVENT2, EVENT3
  }

  class TestStateMachine extends StateMachine {

    public TestStateMachine() {
      super(new MessageQueue());
      State state1 = new State1();
      State state2 = new State2();
      State state3 = new State3();
      new Transition1to2(state1, state2);
      new Transition2to3(state2, state3);
      new Transition3Toend(state3, null);
      setInitState(state1);
    }

    public class State1 extends State{

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

    public class State2 extends State{

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

    public class State3 extends State{

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

    public class Transition1to2 extends Transition{

      public Transition1to2(State fromState, State toState) {
        super(fromState, toState);
      }

      @Override
      protected boolean trigger(SMReception smr) {
        return smr.equals(TestEvents.EVENT1);
      }

      @Override
      protected void effect() {

      }
    }


    public class Transition2to3 extends Transition{

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

    public class Transition3Toend extends Transition{

      public Transition3Toend(State fromState, State toState) {
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

  }

}