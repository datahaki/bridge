// code by jph
package showcase;

import java.awt.Window;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import ch.alpine.bridge.pro.WindowProvider;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

class WindowProviderTest {
  static Collection<Class<?>> allWindowSuppliers() {
    List<Class<?>> list = new LinkedList<>();
    try (ScanResult scan = new ClassGraph().enableAllInfo().acceptPackages("showcase") //
        .scan()) {
      scan.getClassesImplementing(WindowProvider.class.getName()) //
          .loadClasses() //
          .forEach(list::add);
    }
    return list;
  }

  @ParameterizedTest
  @MethodSource("allWindowSuppliers")
  void testWindow(Class<?> cls) throws Exception {
    Constructor<?> constructor = cls.getDeclaredConstructor();
    constructor.setAccessible(true);
    Object object = constructor.newInstance();
    WindowProvider windowSupplier = (WindowProvider) object;
    Window window = windowSupplier.getWindow();
    window.setVisible(true);
    Thread.sleep(1);
    window.setVisible(false);
    window.dispose();
  }
}
