package uml4iot.GenericStateMachine.core.utils;

import javax.annotation.Nonnull;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.String.format;
import static java.lang.Thread.currentThread;
import static org.junit.Assert.fail;


/**
 * {@code BlockingCall} supports blocking call assertion for JUnit tests.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 */
public interface BlockingCall {
  /**import javax.annotation.Nonnull;
   * Calls blocking code.  The blocking code must throw {@code
   * InterruptedException} when its current thread is interrupted.
   *
   * @throws InterruptedException if interrupted.
   */
  void call()
      throws InterruptedException;

  /** Wrapper class for block assertion. */
  public static final class Assert {
    /**
     * Asserts the given <var>code</var> blocks at least 100ms.  Interrupts
     * the blocking code after 100ms and checks {@code InterruptedException}
     * was thrown.  When not blocking, appends <var>block</var> to the
     * failure message.
     *
     * @param block the blocking call, never missing
     */
    public static void assertBlocks(@Nonnull final BlockingCall block) {
      final Timer timer = new Timer(true);
      try {
        final Thread current = currentThread();
        timer.schedule(new TimerTask() {
          @Override
          public void run() {
            current.interrupt();
          }
        }, 100L);

        block.call();
        fail(format("Did not block: %s", block));
      } catch (final InterruptedException ignored) {

      } finally {
        timer.cancel();
      }
    }
  }
}
