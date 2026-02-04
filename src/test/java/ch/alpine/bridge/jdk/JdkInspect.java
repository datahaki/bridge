package ch.alpine.bridge.jdk;

import java.awt.color.ColorSpace;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.classfile.ClassFile;
import java.text.ChoiceFormat;
import java.time.MonthDay;
import java.util.List;

import javax.swing.ImageIcon;

import ch.alpine.bridge.io.HtmlUtf8;
import ch.alpine.tensor.ext.FileExtension;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.ext.ReadLine;

public class JdkInspect {
  static HtmlUtf8 htmlUtf8 = HtmlUtf8.page(HomeDirectory.file("listing.htm"));

  public static void inspect1(File file) {
    ClassFile cf;
    ImageIcon ii;
    MonthDay md;
    try (InputStream is = new FileInputStream(file)) {
      List<String> list = ReadLine.of(is).filter(s -> s.contains(" <<") || s.contains(" >>")).toList();
      if (!list.isEmpty()) {
        IO.println(file);
        list.forEach(s -> System.out.println(" " + s));
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static void inspect2(File file) {
    SecurityManager as;
    MonthDay md;
    ChoiceFormat cf;
    ColorSpace cs;
    try (InputStream is = new FileInputStream(file)) {
      List<String> list = ReadLine.of(is).filter(s -> s.contains("FIXME")).toList();
      if (!list.isEmpty()) {
        System.out.println(file);
        list.forEach(s -> System.out.println(" " + s));
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static void inspect3(File file) {
    MonthDay md;
    try (InputStream is = new FileInputStream(file)) {
      List<String> list = ReadLine.of(is).filter(s -> s.contains("Cloneable")).toList();
      if (!list.isEmpty()) {
        System.out.println(file);
        list.forEach(s -> System.out.println(" " + s));
        htmlUtf8.append("<p>" + file.toString() + "\n");
        htmlUtf8.append("<pre>\n");
        list.forEach(s -> htmlUtf8.append(s + "\n"));
        htmlUtf8.append("</pre>\n");
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static void recur(File dir) {
    for (File file : dir.listFiles()) {
      if (file.isDirectory())
        recur(file);
      else if (FileExtension.of(file).equals("java"))
        inspect3(file);
    }
  }

  static void main() {
    File dir = HomeDirectory.Documents("external/java25");
    recur(dir);
    htmlUtf8.close();
  }
}
