// code by chatgpt
package ch.alpine.bridge.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/** brutal way of task managing */
public class InterruptibleSingleTaskExecutor {
  // Underlying single-thread executor to ensure sequential execution
  private final ExecutorService internalExecutor = Executors.newSingleThreadExecutor();
  // Reference to the currently running task's Future
  private Future<?> activeTaskFuture;
  private final Object lock = new Object();

  /** Submits a new task. If a task is currently running, it will be interrupted,
   * and the new task will take its place. */
  public Future<?> submit(Runnable task) {
    synchronized (lock) {
      // If an active task exists and is not finished, interrupt it
      if (activeTaskFuture != null && !activeTaskFuture.isDone()) {
        // canon -> sparrow
        activeTaskFuture.cancel(true); // true = Thread.interrupt() if running
      }
      // Submit the new task to the single-thread executor
      activeTaskFuture = internalExecutor.submit(() -> {
        try {
          // Periodically check Thread.interrupted() inside your task logic
          // if your task performs long-running CPU or blocking operations.
          task.run();
        } catch (Throwable t) {
          if (Thread.currentThread().isInterrupted() || t instanceof InterruptedException) {
            System.out.println("Previous task was successfully interrupted.");
          } else {
            t.printStackTrace();
          }
        }
      });
      return activeTaskFuture;
    }
  }

  /** Shuts down the underlying executor service. */
  public void shutdown() {
    synchronized (lock) {
      if (activeTaskFuture != null) {
        activeTaskFuture.cancel(true);
      }
      internalExecutor.shutdownNow();
    }
  }
}
