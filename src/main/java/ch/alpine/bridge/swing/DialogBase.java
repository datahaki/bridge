// code by jph
package ch.alpine.bridge.swing;

/* package */ abstract class DialogBase<T> implements DialogBuilder<T> {
  private final T fallback;

  protected DialogBase(T fallback) {
    this.fallback = fallback;
  }

  @Override
  public final T fallback() {
    return fallback;
  }
}
