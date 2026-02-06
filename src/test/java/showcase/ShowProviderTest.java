package showcase;

import java.awt.Dimension;
import java.lang.reflect.Constructor;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.lang.ShowProvider;
import ch.alpine.tensor.ext.HomeDirectory;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

class ShowProviderTest {
  @TempDir
  Path tempDir;

  static Collection<Class<?>> allWindowSuppliers() {
    List<Class<?>> list = new LinkedList<>();
    try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages("ch", "showcase") //
        .scan()) {
      scanResult.getClassesImplementing(ShowProvider.class.getName()) //
          .loadClasses() //
          .forEach(list::add);
    }
    return list;
  }

  @ParameterizedTest
  @MethodSource("allWindowSuppliers")
  void testWindow(Class<?> cls) throws Exception {
    Path folder = HomeDirectory.Pictures.resolve("bridge_showcase");
    // folder.mkdir();
    folder = tempDir;
    if (cls.isEnum()) {
      for (Object object : cls.getEnumConstants()) {
        Enum<?> enm = (Enum<?>) object;
        _check((ShowProvider) object, folder, cls.getSimpleName() + "_" + enm.name());
      }
    } else //
    if (cls.isInterface()) {
    } else //
    if (cls.isAnonymousClass()) {
    } else //
    {
      Constructor<?> constructor = cls.getDeclaredConstructor();
      constructor.setAccessible(true);
      Object object = constructor.newInstance();
      _check((ShowProvider) object, folder, cls.getSimpleName());
    }
  }

  public static void _check(ShowProvider showProvider, Path tempDir, String string) {
    Show show = showProvider.getShow();
    Path file = tempDir.resolve(string + ".png");
    IO.println(file.getFileName());
    try {
      show.export(file, new Dimension(400, 300));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
