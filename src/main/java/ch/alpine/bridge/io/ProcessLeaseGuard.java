// code by gemini
package ch.alpine.bridge.io;

import java.awt.GraphicsEnvironment;
import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class ProcessLeaseGuard {
  @FunctionalInterface
  public interface AlreadyRunningHandler {
    void onAlreadyRunning(Path lockFile);
  }

  /** Represents an acquired cross-JVM lock lease.
   * The caller is responsible for calling {@link #close()} when the lock should be released. */
  public static class Lease implements Closeable {
    private final FileChannel fileChannel;
    private final FileLock fileLock;
    private final AtomicBoolean closed = new AtomicBoolean(false);

    private Lease(FileChannel fileChannel, FileLock fileLock) {
      this.fileChannel = fileChannel;
      this.fileLock = fileLock;
    }

    public boolean isOpen() {
      return !closed.get() //
          && fileChannel.isOpen() //
          && fileLock.isValid();
    }

    @Override
    public void close() {
      if (!closed.compareAndSet(false, true))
        return;
      UncheckedIOException firstError = null;
      try {
        if (fileLock.isValid())
          fileLock.release();
      } catch (IOException ioException) {
        firstError = new UncheckedIOException(ioException);
      }
      try {
        if (fileChannel.isOpen())
          fileChannel.close();
      } catch (IOException ioException) {
        UncheckedIOException wrapped = new UncheckedIOException(ioException);
        if (firstError == null)
          firstError = wrapped;
        else
          firstError.addSuppressed(wrapped);
      }
      if (firstError != null)
        throw firstError;
    }
  }

  private final Path path;
  private final AlreadyRunningHandler alreadyRunningHandler;

  public ProcessLeaseGuard(Class<?> cls) {
    this(cls.getName());
  }

  public ProcessLeaseGuard(String string) {
    this(defaultLockFile(string), ProcessLeaseGuard::defaultNotifyHandler);
  }

  public ProcessLeaseGuard(Path lockFile, AlreadyRunningHandler alreadyRunningHandler) {
    this.path = Objects.requireNonNull(lockFile, "lockFile must not be null");
    this.alreadyRunningHandler = Objects.requireNonNull(alreadyRunningHandler, "alreadyRunningHandler must not be null");
  }

  public static Path defaultLockFile(String lockName) {
    Objects.requireNonNull(lockName, "lockName must not be null");
    String sanitized = lockName.replaceAll("[^a-zA-Z0-9._-]", "_");
    String user = System.getProperty("user.name", "user");
    return Paths.get(System.getProperty("java.io.tmpdir"), sanitized + "-" + user + ".lock");
  }

  public static void defaultNotifyHandler(Path path) {
    if (GraphicsEnvironment.isHeadless()) {
      System.err.println("Another instance is already active (lock: " + path + "). Exiting.");
    } else {
      Runnable runnable = () -> JOptionPane.showMessageDialog(null, //
          "The window is already open in another session.\n" + path, //
          "Process Already Active", JOptionPane.INFORMATION_MESSAGE);
      SwingUtilities.invokeLater(runnable);
    }
  }

  public Optional<Lease> tryAcquire() {
    FileChannel fileChannel;
    try {
      fileChannel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
    } catch (IOException ioException) {
      throw new UncheckedIOException(ioException);
    }
    try {
      FileLock fileLock;
      try {
        fileLock = fileChannel.tryLock();
      } catch (OverlappingFileLockException e) {
        fileLock = null;
      }
      if (fileLock == null) {
        closeChannelQuietly(fileChannel);
        alreadyRunningHandler.onAlreadyRunning(path);
        return Optional.empty();
      }
      return Optional.of(new Lease(fileChannel, fileLock));
    } catch (IOException ioException) {
      closeChannelQuietly(fileChannel);
      throw new UncheckedIOException(ioException);
    } catch (RuntimeException | Error exception) {
      closeChannelQuietly(fileChannel);
      throw exception;
    }
  }

  public boolean ifAvailable(Consumer<Lease> consumer) {
    Objects.requireNonNull(consumer, "onAcquired must not be null");
    Optional<Lease> optional = tryAcquire();
    if (optional.isEmpty())
      return false;
    Lease lease = optional.orElseThrow();
    try {
      consumer.accept(lease);
      return true;
    } catch (RuntimeException | Error error) {
      closeLeaseQuietly(lease);
      throw error;
    }
  }

  private static void closeChannelQuietly(FileChannel fileChannel) {
    if (fileChannel != null && fileChannel.isOpen()) {
      try {
        fileChannel.close();
      } catch (IOException ignored) {
        // Best-effort cleanup on failed acquisition
      }
    }
  }

  private static void closeLeaseQuietly(Lease lease) {
    try {
      lease.close();
    } catch (UncheckedIOException ignored) {
      // Best-effort cleanup if caller callback failed before taking ownership
    }
  }
}
