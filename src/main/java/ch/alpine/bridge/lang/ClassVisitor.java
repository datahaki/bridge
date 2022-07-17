// code by lcm
package ch.alpine.bridge.lang;

@FunctionalInterface
public interface ClassVisitor {
  /** @param jarfile
   * @param cls */
  void accept(String jarfile, Class<?> cls);
}
