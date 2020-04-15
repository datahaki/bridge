// code by lcm
package ch.ethz.idsc.java.lang;

@FunctionalInterface
public interface ClassVisitor {
  /** @param jarfile
   * @param cls */
  void classFound(String jarfile, Class<?> cls);
}