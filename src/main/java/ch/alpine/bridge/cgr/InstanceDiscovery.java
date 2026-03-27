// code by jph
package ch.alpine.bridge.cgr;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import ch.alpine.bridge.io.GitHubCI;
import ch.alpine.tensor.ext.PackageTestAccess;

/** implementation of class visitor to extract implementation of cls */
public record InstanceDiscovery<T>(String basePackage, Class<T> cls, Consumer<InstanceRecord<T>> consumer) implements ClassVisitor {
  /** function to discover instances of a certain class nested in basePackage
   * 
   * Example use:
   * generate dynamic test from the instances in the returned list
   * 
   * @param basePackage for instance getClass().getPackageName()
   * @param cls
   * @return
   * @throws Exception */
  public static <T> List<InstanceRecord<T>> of(String basePackage, Class<T> cls) throws Exception {
    List<InstanceRecord<T>> list = new LinkedList<>();
    ClassDiscovery.execute(ClassPaths.getDefault(), //
        new InstanceDiscovery<>(basePackage, cls, list::add));
    return list;
  }

  @Override // from ClassVisitor
  public void accept(String jarfile, Class<?> subcls) {
    if (isInSubpackageOf(subcls, basePackage) && //
        cls.isAssignableFrom(subcls)) { // this narrow is deliberate
      GitHubCI.println("Extracting candidates in: " + subcls.getName());
      for (Field field : subcls.getDeclaredFields())
        if (Modifier.isStatic(field.getModifiers())) {
          try {
            field.trySetAccessible(); // mandatory
            Object object = field.get(null);
            if (cls.isInstance(object)) {
              T cast = cls.cast(object); // already loaded in memory
              InstanceRecord<T> instanceRecord = new InstanceRecord<T>(subcls, field, () -> cast);
              consumer.accept(instanceRecord);
            }
          } catch (Exception exception) {
            System.err.println("error " + exception);
          }
        }
      // ---
      if (subcls.isEnum()) {
        // enum constants are handled as fields above
      } else //
      if (subcls.isInterface()) {
        // ---
      } else //
      if (subcls.isRecord()) {
        // ---
      } else //
      if (subcls.isAnonymousClass()) {
        // ---
      } else //
        try {
          {
            Constructor<?> constructor = subcls.getDeclaredConstructor();
            constructor.trySetAccessible();
            Object object = constructor.newInstance();
            cls.cast(object);
          }
          Supplier<T> supplier = () -> {
            try {
              Constructor<?> constructor = subcls.getDeclaredConstructor();
              constructor.trySetAccessible();
              Object object = constructor.newInstance();
              return cls.cast(object);
            } catch (Exception exception) {
              throw new RuntimeException(exception);
            }
          };
          InstanceRecord<T> instanceRecord = new InstanceRecord<T>(subcls, null, supplier);
          consumer.accept(instanceRecord);
        } catch (Exception exception) {
          // default constructor may not exist
        }
    }
  }

  @PackageTestAccess
  static boolean isInSubpackageOf(Class<?> clazz, String basePackage) {
    Package pkg = clazz.getPackage();
    if (pkg == null)
      return false;
    String string = pkg.getName();
    return string.equals(basePackage) //
        || string.startsWith(basePackage + ".");
  }
}
