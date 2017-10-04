package uml4iot.GenericStateMachine.core;
import java.util.ArrayList;
import org.junit.*;
import uml4iot.GenericStateMachine.core.utils.BlockingCall;

import static org.junit.Assert.*;
import static uml4iot.GenericStateMachine.core.utils.BlockingCall.Assert.*;

public class MessageQueueTest {

  MessageQueue queue;

  @Before
  public void reinitialize(){
    queue = new MessageQueue();
  }


  @Test
  public void testInsertAndPop(){
    assertEquals(queue.size(), 0);

    queue.add(TestEvents.EVENT1);
    assertEquals(queue.size(), 1);

    SMReception popedEvent = null;
    try {
      popedEvent = queue.getNext();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertEquals(popedEvent, TestEvents.EVENT1);
    assertEquals(queue.size(), 0);
  }


  @Test
  public void testInsertAndPopWithIgnore(){
    ArrayList<SMReception> ignoreSet = new ArrayList<>();
    ignoreSet.add(TestEvents.EVENT1);

    assertEquals(queue.size(), 0);

    queue.add(TestEvents.EVENT1);
    assertEquals(queue.size(), 1);

    queue.add(TestEvents.EVENT2);
    assertEquals(queue.size(), 2);

    SMReception popedEvent = null;
    try {
      popedEvent = queue.getNext(ignoreSet);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertEquals(popedEvent, TestEvents.EVENT2);
    assertEquals(queue.size(), 1);

    assertBlocks(() -> queue.getNext(ignoreSet));


  }


  enum TestEvents implements SMReception{
    EVENT1, EVENT2, EVENT3
  }

}
