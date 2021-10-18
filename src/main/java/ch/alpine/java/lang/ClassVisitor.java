// code by lcm
package ch.alpine.java.lang;

@FunctionalInterface
public interface ClassVisitor {
  /** @param jarfile
   * @param cls */
  void accept(String jarfile, Class<?> cls);
}