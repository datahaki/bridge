// code by lcm
package ch.alpine.java.lang;

@FunctionalInterface
public interface ClassVisitor {
  /** @param jarfile
   * @param cls */
  void classFound(String jarfile, Class<?> cls);
}