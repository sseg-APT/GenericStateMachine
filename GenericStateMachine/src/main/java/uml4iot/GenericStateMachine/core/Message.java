package uml4iot.GenericStateMachine.core;


import java.util.ArrayList;
import java.util.List;

public class Message implements  SMReception {

  private Enum id;
  private List<Object> arguments;

  public Message(Enum id, List<Object> arguments)
  {
    this.id = id;
    this.arguments = arguments;
  }

  public Message(Enum id)
  {
    this.id = id;
    arguments = new ArrayList<>(3);
  }

  public void addArgument(Object arg)
  {
    arguments.add(arg);
  }

  public <T> T getArgument(int index, Class<T> type){
    return type.cast(arguments.get(index));
  }

  public Enum getId()
  {
    return id;
  }


}
