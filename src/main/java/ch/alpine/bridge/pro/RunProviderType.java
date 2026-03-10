// code by jph
package ch.alpine.bridge.pro;

import java.util.Objects;

enum RunProviderType {
  VOID(VoidProvider.class),
  SHOW(ShowProvider.class),
  MANIPULATE(ManipulateProvider.class),
  WINDOW(WindowProvider.class);

  private final Class<? extends RunProvider> cls;

  RunProviderType(Class<? extends RunProvider> cls) {
    this.cls = cls;
  }

  public static RunProviderType getType(Class<?> subcls) {
    Objects.requireNonNull(subcls);
    for (RunProviderType runProviderType : values())
      if (runProviderType.cls.isAssignableFrom(subcls))
        return runProviderType;
    throw new IllegalArgumentException(subcls.toString());
  }
}
