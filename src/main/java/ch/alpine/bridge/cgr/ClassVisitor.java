// code by lcm
package ch.alpine.bridge.cgr;

@FunctionalInterface
public interface ClassVisitor {
  /** @param jarfile
   * @param cls */
  void accept(String jarfile, Class<?> cls);
}
