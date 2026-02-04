// code by jph
package sys.io;

import java.io.Writer;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

import ch.alpine.bridge.lang.FriendlyFormat;

public abstract class XmlWriter {
  private final Writer writer;
  private final Deque<String> deque = new ArrayDeque<>();

  public XmlWriter(Writer writer) {
    this.writer = Objects.requireNonNull(writer);
  }

  protected void push(String string) throws Exception {
    writeln("<" + string + ">");
    deque.push(string);
  }

  protected void push(String string, String params) throws Exception {
    writeln("<" + string + " " + params + ">");
    deque.push(string);
  }

  protected void pop(String string) throws Exception {
    String verify = deque.peek(); // the object at the top of this stack without removing it from the stack
    if (!verify.equals(string))
      throw new RuntimeException(string + ", but expected: " + verify);
    pop();
  }

  private void pop() throws Exception {
    String string = deque.pop();
    writeln("</" + string + ">");
  }

  protected void content(String string) throws Exception {
    writeln(FriendlyFormat.convertChars(string));
  }

  protected void writeln(String string) throws Exception {
    writer.write(" ".repeat(deque.size()) + string);
    writer.write('\n');
  }

  protected boolean isStackEmpty() {
    return deque.isEmpty();
  }

  protected void checkStackEmpty() {
    if (!isStackEmpty())
      new RuntimeException("stack not empty").printStackTrace();
  }

  protected void flush() throws Exception {
    writer.flush();
  }

  protected void close() throws Exception {
    writer.close();
    // ---
    checkStackEmpty();
  }
}
