// code by jph
package ch.alpine.bridge.pro;

public enum RunProviderType {
  VOID(VoidProvider.class),
  WINDOW(WindowProvider.class),
  SHOW(ShowProvider.class),
  MANIPULATE(ManipulateProvider.class);

  private final Class<? extends RunProvider> cls;

  RunProviderType(Class<? extends RunProvider> cls) {
    this.cls = cls;
  }

  public static RunProviderType getType(Class<?> subcls) {
    for (RunProviderType rpt : values())
      if (rpt.cls.isAssignableFrom(subcls))
        return rpt;
    throw new RuntimeException();
  }
}
