// code by jph
package sys;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;

import sys.dat.ManagerOp;
import sys.mat.TokenStream;

/** configuration required prior use
 * 
 * <pre>
 * git config --global user.name "John Smith"
 * git config --global user.email john@example.com
 * git config --global core.autocrlf false
 * </pre> */
public class Git {
  private static String getExecutable() {
    return ""; // FIXME
  }

  public final File myDirectory;
  public final File sha;
  public final List<String> myManager_execute;

  public Git(File myDirectory) throws Exception {
    this.myDirectory = myDirectory;
    if (!new File(myDirectory, ".git").exists()) {
      System.out.println("git does not exist ... init");
      init();
    }
    File gitIgnore = new File(myDirectory, ".gitignore");
    if (!gitIgnore.exists())
      try (PrintStream myPrintStream = new PrintStream(gitIgnore)) {
        myPrintStream.println(".gitignore");
        myPrintStream.println("sha.properties");
      }
    sha = new File(myDirectory, "sha.properties");
    myManager_execute = ManagerOp.lines(sha);
  }

  public static String version() {
    try {
      return static_process(new ProcessBuilder(getExecutable(), "--version"));
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    return null;
  }

  private void init() throws Exception {
    process(new ProcessBuilder(getExecutable(), "init"));
  }

  private void add_A() throws Exception {
    process(new ProcessBuilder(getExecutable(), "add", "-A"));
  }

  private void commit() throws Exception {
    process(new ProcessBuilder(getExecutable(), "commit", "-a", "-m", "noname"));
  }

  public String revParse() throws Exception {
    String myString = process(new ProcessBuilder(getExecutable(), "rev-parse", "HEAD"));
    return myString.trim();
  }

  public List<String> revList() throws Exception {
    String string = process(new ProcessBuilder(getExecutable(), "rev-list", "--all"));
    return TokenStream.of(string).collect(Collectors.toList());
  }

  public void manifest() throws Exception {
    String myPrev = revParse();
    add_A();
    commit();
    String myNext = revParse();
    if (!myManager_execute.contains(myNext)) {
      int index = myManager_execute.indexOf(myPrev);
      myManager_execute.add(Math.max(0, index), myNext);
      ManagerOp.manifest(myManager_execute, sha);
    }
  }

  private synchronized String process(ProcessBuilder myProcessBuilder) throws Exception {
    myProcessBuilder.directory(myDirectory);
    return static_process(myProcessBuilder);
  }

  private static String static_process(ProcessBuilder myProcessBuilder) throws Exception {
    // long myLong = System.nanoTime();
    Process process = myProcessBuilder.start();
    process.waitFor();
    // myLong = System.nanoTime() - myLong;
    // TODO TPF generic
    try (InputStream inputStream = process.getInputStream()) {
      byte[] data = new byte[inputStream.available()];
      inputStream.read(data);
      return new String(data);
    }
    // if (myString.length() <= 512)
    // System.out.println(myString);
    // System.out.println(String.format("--> git %.2f sec.", myLong * 1e-9));
  }

  public void checkout(String myString) throws Exception {
    process(new ProcessBuilder(getExecutable(), "checkout", myString));
  }
}
