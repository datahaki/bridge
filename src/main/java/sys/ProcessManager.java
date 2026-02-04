// code by jph
package sys;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public final class ProcessManager {
  private final List<ProcessBuilder> list;

  /** @param list will not be modified (but contents might) */
  public ProcessManager(List<ProcessBuilder> list) {
    this.list = list;
    // ---
    list.forEach(this::safe);
  }

  private void safe(ProcessBuilder processBuilder) {
    switch (OperatingSystem.TYPE) {
    case WINDOWS:
      List<String> command = processBuilder.command();
      String exec = command.get(0);
      if (!exec.startsWith("\"") && exec.contains(" "))
        command.set(0, '\"' + exec + '\"');
      break;
    default:
      break;
    }
  }

  public ProcessManager(ProcessBuilder processBuilder) {
    this(List.of(processBuilder));
  }

  public Process executeSafeWaitFor(File file) throws Exception {
    Process process = executeSafe(file);
    process.waitFor();
    return process;
  }

  public boolean bat = true;
  public boolean log = true;

  public void setSilent() {
    bat = false;
    log = false;
  }

  public Process executeSafe(File file) {
    Filename filename = new Filename(file);
    File directory = file.getParentFile();
    File batchFile = filename.withExtension("bat");
    if (bat)
      toBatchFile(batchFile);
    Process process = null;
    try {
      switch (OperatingSystem.TYPE) {
      case WINDOWS: {
        // execute as batch
        toBatchFile(batchFile);
        if (!bat)
          batchFile.deleteOnExit();
        ProcessBuilder processBuilder = new ProcessBuilder(batchFile.getAbsolutePath());
        processBuilder.directory(directory);
        if (log)
          processBuilder.redirectError(filename.withExtension("log"));
        process = processBuilder.start();
        break;
      }
      case LINUX:
        int count = 0;
        for (ProcessBuilder processBuilder : list) {
          processBuilder.directory(directory);
          if (log)
            processBuilder.redirectError(filename.withExtension("log")); // meaningful for list of tasks!?
          process = processBuilder.start();
          ++count;
          if (count < list.size())
            process.waitFor();
        }
        break;
      default:
        break;
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    return process;
  }

  @Override
  public String toString() {
    StringBuilder stringBuffer = new StringBuilder();
    for (ProcessBuilder processBuilder : list) {
      StringBuffer commandBuffer = new StringBuffer();
      processBuilder.command().forEach(s -> commandBuffer.append(" " + s));
      stringBuffer.append(commandBuffer.substring(1) + '\n');
    }
    return stringBuffer.toString();
  }

  public void toBatchFile(File file) {
    try {
      Files.write(file.toPath(), toString().getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
