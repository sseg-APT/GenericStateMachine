package uml4iot.GenericStateMachine.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import java.lang.reflect.Type;


public abstract class BaseSignal implements SMReception {

  static class InstanceCreatorWithInstance<T> implements InstanceCreator<T>
  {
    T instance;

    public InstanceCreatorWithInstance(T instance)
    {
      this.instance = instance;
    }

    @Override
    public T createInstance(Type type)
    {
      return instance;
    }
  }

  public String sender, receiver, timestamp;

  public BaseSignal(){
  }

  public BaseSignal(String from, String to){
    sender = from;
    receiver = to;
  }

    public BaseSignal(String json){
    Gson gson = new GsonBuilder().registerTypeAdapter(this.getClass(), new InstanceCreatorWithInstance<>(this)).create();
    gson.fromJson(json, this.getClass());
  }


}


