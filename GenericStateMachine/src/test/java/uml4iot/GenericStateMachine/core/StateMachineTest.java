package uml4iot.GenericStateMachine.core;

import java.util.concurrent.ArrayBlockingQueue;
import org.junit.*;
import uml4iot.GenericStateMachine.core.StateMachineTest.TestStateMachine.Job;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;



public class StateMachineTest {

  TestStateMachine stateMachine;
  Thread stateThread;

  @Before
  public void initialize(){
    stateMachine = new TestStateMachine();
    stateThread = new Thread(stateMachine);
    stateThread.start();
  }


  @Test
  public void testStatesTransitioning() throws InterruptedException {
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

  @Test
  public void testEntry() throws InterruptedException {

    Job job = stateMachine.entryQueue.take();
    assertEquals(stateMachine.entryQueue.size(), 0);
    assertEquals(job.className, TestStateMachine.State1.class);

    stateMachine.itsMsgQ.add(TestEvents.EVENT1);
    job = stateMachine.entryQueue.take();
    assertEquals(stateMachine.entryQueue.size(), 0);
    assertEquals(job.className, TestStateMachine.State2.class);

    stateMachine.itsMsgQ.add(TestEvents.EVENT2);
    job = stateMachine.entryQueue.take();
    assertEquals(stateMachine.entryQueue.size(), 0);
    assertEquals(job.className, TestStateMachine.State3.class);

    stateMachine.itsMsgQ.add(TestEvents.EVENT3);
    Thread.sleep(100);
    assertEquals(stateThread.getState(), Thread.State.TERMINATED);
    assertEquals(stateMachine.entryQueue.size(), 0);

  }


  @Test
  public void testDoActivity() throws InterruptedException{

    Job job = stateMachine.doActivityQueue.take();
    assertEquals(stateMachine.doActivityQueue.size(), 0);
    assertEquals(job.className, TestStateMachine.State1.class);

    stateMachine.itsMsgQ.add(TestEvents.EVENT1);
    job = stateMachine.doActivityQueue.take();
    assertEquals(stateMachine.doActivityQueue.size(), 0);
    assertEquals(job.className, TestStateMachine.State2.class);

    stateMachine.itsMsgQ.add(TestEvents.EVENT2);
    job = stateMachine.doActivityQueue.take();
    assertEquals(stateMachine.doActivityQueue.size(), 0);
    assertEquals(job.className, TestStateMachine.State3.class);

    stateMachine.itsMsgQ.add(TestEvents.EVENT3);
    Thread.sleep(100);
    assertEquals(stateThread.getState(), Thread.State.TERMINATED);
    assertEquals(stateMachine.doActivityQueue.size(), 0);
  }


  @Test
  public void testExit() throws InterruptedException{

    assertEquals(stateMachine.exitQueue.size(), 0);

    stateMachine.itsMsgQ.add(TestEvents.EVENT1);
    Job job = stateMachine.exitQueue.take();
    assertEquals(stateMachine.exitQueue.size(), 0);
    assertEquals(job.className, TestStateMachine.State1.class);

    stateMachine.itsMsgQ.add(TestEvents.EVENT2);
    job = stateMachine.exitQueue.take();
    assertEquals(stateMachine.exitQueue.size(), 0);
    assertEquals(job.className, TestStateMachine.State2.class);

    stateMachine.itsMsgQ.add(TestEvents.EVENT3);
    job = stateMachine.exitQueue.take();
    assertEquals(stateMachine.exitQueue.size(), 0);
    assertEquals(job.className, TestStateMachine.State3.class);

    Thread.sleep(100);
    assertEquals(stateThread.getState(), Thread.State.TERMINATED);
    assertEquals(stateMachine.exitQueue.size(), 0);
  }

  @Test
  public void testEffect() throws InterruptedException{

    assertEquals(stateMachine.effectQueue.size(), 0);

    stateMachine.itsMsgQ.add(TestEvents.EVENT1);
    Job job = stateMachine.effectQueue.take();
    assertEquals(stateMachine.effectQueue.size(), 0);
    assertEquals(job.className, TestStateMachine.Transition1to2.class);

    stateMachine.itsMsgQ.add(TestEvents.EVENT2);
    job = stateMachine.effectQueue.take();
    assertEquals(stateMachine.effectQueue.size(), 0);
    assertEquals(job.className, TestStateMachine.Transition2to3.class);

    stateMachine.itsMsgQ.add(TestEvents.EVENT3);
    job = stateMachine.effectQueue.take();
    assertEquals(stateMachine.effectQueue.size(), 0);
    assertEquals(job.className, TestStateMachine.Transition3Toend.class);

    Thread.sleep(100);
    assertEquals(stateThread.getState(), Thread.State.TERMINATED);
    assertEquals(stateMachine.effectQueue.size(), 0);
  }

  @Test
  public void testJobsOrder() throws InterruptedException{

    Job job = stateMachine.entryQueue.take();
    assertEquals(0, job.jobOrder);

    job = stateMachine.doActivityQueue.take();
    assertEquals(1, job.jobOrder);

    stateMachine.itsMsgQ.add(TestEvents.EVENT1);

    job = stateMachine.exitQueue.take();
    assertEquals(2, job.jobOrder);

    job = stateMachine.effectQueue.take();
    assertEquals(3, job.jobOrder);

    job = stateMachine.entryQueue.take();
    assertEquals(4, job.jobOrder);

    job = stateMachine.doActivityQueue.take();
    assertEquals(5, job.jobOrder);

    stateMachine.itsMsgQ.add(TestEvents.EVENT2);

    job = stateMachine.exitQueue.take();
    assertEquals(6, job.jobOrder);

    job = stateMachine.effectQueue.take();
    assertEquals(7, job.jobOrder);

    job = stateMachine.entryQueue.take();
    assertEquals(8, job.jobOrder);

    job = stateMachine.doActivityQueue.take();
    assertEquals(9, job.jobOrder);

    stateMachine.itsMsgQ.add(TestEvents.EVENT3);

    job = stateMachine.exitQueue.take();
    assertEquals(10, job.jobOrder);

    job = stateMachine.effectQueue.take();
    assertEquals(11, job.jobOrder);

    assertEquals(0, stateMachine.entryQueue.size());
    assertEquals(0, stateMachine.doActivityQueue.size());
    assertEquals(0, stateMachine.exitQueue.size());
    assertEquals(0, stateMachine.effectQueue.size());
  }






  //----------------Testing State Machine--------------

  enum TestEvents implements SMReception{
    EVENT1, EVENT2, EVENT3
  }

  class TestStateMachine extends StateMachine {

    class Job{

      int jobOrder;
      Class className;

      public Job(int jobOrder, Class className){
        this.jobOrder = jobOrder;
        this.className = className;
      }
    }

    int currentJob = 0;
    public ArrayBlockingQueue<Job> entryQueue = new ArrayBlockingQueue<>(5);
    public ArrayBlockingQueue<Job> doActivityQueue = new ArrayBlockingQueue<>(5);
    public ArrayBlockingQueue<Job> exitQueue = new ArrayBlockingQueue<>(5);
    public ArrayBlockingQueue<Job> effectQueue = new ArrayBlockingQueue<>(5);

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
        entryQueue.add(new Job(currentJob++, State1.class));

      }

      @Override
      protected void doActivity() {
        doActivityQueue.add(new Job(currentJob++, State1.class));
      }

      @Override
      protected void exit() {
        exitQueue.add(new Job(currentJob++, State1.class));
      }
    }

    public class State2 extends State{

      @Override
      protected void entry() {
        entryQueue.add(new Job(currentJob++, State2.class));

      }

      @Override
      protected void doActivity() {
        doActivityQueue.add(new Job(currentJob++, State2.class));

      }

      @Override
      protected void exit() {
        exitQueue.add(new Job(currentJob++, State2.class));
      }
    }

    public class State3 extends State{

      @Override
      protected void entry() {
        entryQueue.add(new Job(currentJob++, State3.class));

      }

      @Override
      protected void doActivity() {
        doActivityQueue.add(new Job(currentJob++, State3.class));
      }

      @Override
      protected void exit() {
        exitQueue.add(new Job(currentJob++, State3.class));
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
        effectQueue.add(new Job(currentJob++, Transition1to2.class));

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
        effectQueue.add(new Job(currentJob++, Transition2to3.class));
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
        effectQueue.add(new Job(currentJob++, Transition3Toend.class));
      }
    }

  }

}