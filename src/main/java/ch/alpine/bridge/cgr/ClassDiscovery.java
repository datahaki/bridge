// code by lcm
// adapted by jph
package ch.alpine.bridge.cgr;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.jar.JarFile;

public record ClassDiscovery(String classpath, ClassVisitor classVisitor) {
  /** @param classpath
   * @param classVisitor
   * @throws Exception */
  public static void execute(String classpath, ClassVisitor classVisitor) throws Exception {
    new ClassDiscovery(classpath, classVisitor).findClasses();
  }

  /** Given a colon-delimited list of jar files, iterate over the classes in them.
   * 
   * @throws Exception */
  private void findClasses() throws Exception {
    String[] items = classpath.split(File.pathSeparator);
    URL[] urls = new URL[items.length];
    for (int index = 0; index < items.length; ++index)
      urls[index] = new File(items[index]).toURI().toURL();
    // Create a class loader that has access to the whole class path.
    try (URLClassLoader urlClassLoader = new URLClassLoader(urls)) {
      for (String item : items)
        if (item.endsWith(".jar"))
          try (JarFile jarFile = new JarFile(item)) {
            jarFile.stream().forEach(jarEntry -> {
              String name = jarEntry.getName();
              // skip private classes?
              if (name.endsWith(".class")) {
                // convert the path into a class name
                String className = name.substring(0, name.length() - 6);
                className = className.replace('/', '.');
                className = className.replace('\\', '.');
                // try loading that class
                try {
                  Class<?> cls = urlClassLoader.loadClass(className);
                  if (Objects.nonNull(cls))
                    recur(item, cls);
                } catch (ClassNotFoundException e) {
                  // Expected: Some .class files may not be loadable
                } catch (NoClassDefFoundError e) {
                  // Expected: Missing dependencies
                } catch (Exception e) {
                  // Unexpected: Log for debugging
                  System.err.println("Unexpected error loading " + className + ": " + e);
                }
              }
            });
          } catch (IOException ioException) {
            IO.println("Error extracting " + item);
          }
        else {
          Path path = Path.of(item);
          if (Files.isDirectory(path))
            visitDirectory(urlClassLoader, item, path, "");
        }
    }
  }

  private void recur(String item, Class<?> cls) {
    classVisitor.accept(item, cls);
    for (Class<?> dec : cls.getDeclaredClasses())
      recur(item, dec);
  }

  private void visitDirectory(URLClassLoader cldr, String classpath_entry, Path path, String visiting_classpath) {
    File dir = path.toFile();
    if (!dir.canRead())
      return;
    for (File file : dir.listFiles()) {
      if (!file.canRead())
        continue;
      String fname = file.getName();
      if (file.isDirectory()) {
        // found a directory. recursively traverse the directory and
        // search for .class files
        if (fname.contains("."))
          continue;
        // Modified by Jan in order to enable nested packages
        String vc = visiting_classpath.isEmpty() ? fname : visiting_classpath + "." + fname;
        visitDirectory(cldr, classpath_entry, file.toPath(), vc);
      } else //
      if (file.isFile() && fname.endsWith(".class")) {
        // found a .class file. Construct its full classname and pass
        // it to the class visitor
        // Modified by Jan in order to enable nested packages
        String className = visiting_classpath + "." + fname.substring(0, fname.length() - 6);
        try {
          Class<?> cls = cldr.loadClass(className);
          if (Objects.nonNull(cls))
            classVisitor.accept(classpath_entry, cls);
        } catch (ClassNotFoundException e) {
          // Expected: Some .class files may not be loadable
        } catch (NoClassDefFoundError e) {
          // Expected: Missing dependencies
        } catch (Exception e) {
          // Unexpected: Log for debugging
          System.err.println("Unexpected error loading " + className + ": " + e);
        }
      }
    }
  }
}
