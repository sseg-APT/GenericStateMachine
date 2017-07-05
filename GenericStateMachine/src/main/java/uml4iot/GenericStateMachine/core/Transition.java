package uml4iot.GenericStateMachine.core;


public abstract class Transition extends BaseTransition<SMReception>{

  public Transition(State fromState, State toState, boolean fork, boolean join,
      boolean completion) {
    super(fromState, toState, fork, join, completion);
  }

  public Transition(State fromState, State toState){
    super(fromState, toState);
  }

}
