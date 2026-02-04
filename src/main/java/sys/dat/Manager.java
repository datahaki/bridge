// code by jph
package sys.dat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Manager {
  public static final Charset CHARSET = StandardCharsets.UTF_8;
  // ---
  private final Properties properties = new Properties();
  private final File file; // place to read and possibly update properties

  public Manager(File file) {
    this.file = file;
    if (Objects.nonNull(file))
      try {
        if (file.createNewFile())
          System.out.println("created " + file);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), CHARSET))) {
          properties.load(bufferedReader);
        }
      } catch (Exception exception) {
        exception.printStackTrace();
      }
  }

  public Manager() {
    this(null);
  }

  public void manifest() {
    if (Objects.nonNull(file))
      try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), CHARSET))) {
        SortedSet<String> sortedSet = new TreeSet<>(properties.stringPropertyNames());
        if (!sortedSet.isEmpty()) {
          for (String string : sortedSet)
            writer.write(string.trim() + "=" + properties.getProperty(string).trim() + '\n');
          writer.write('\n');
        }
      } catch (Exception exception) {
        exception.printStackTrace();
      }
  }

  /** @return The returned set is not backed by the Properties object. Changes to this Properties are not reflected in the set, or vice versa. */
  public Set<String> keySet() {
    return properties.stringPropertyNames();
  }

  public Set<Entry<String, String>> entrySet() { // this could be a stream, but would have to case Object -> String
    Map<String, String> map = new HashMap<>();
    properties.entrySet().stream() //
        .forEach(myEntry -> map.put((String) myEntry.getKey(), (String) myEntry.getValue()));
    return map.entrySet();
  }

  public void setProperty(String key, Object value) {
    properties.setProperty(key, value.toString());
  }

  public void remove(String myString) {
    properties.remove(myString);
  }

  public void clear() {
    properties.clear();
  }

  public boolean containsKey(String string) {
    return properties.containsKey(string);
  }

  public boolean getBoolean(String string, boolean fallback) {
    try {
      return Boolean.parseBoolean(properties.getProperty(string).trim());
    } catch (Exception exception) {
      // ---
    }
    return fallback;
  }

  public int getInteger(String string, int fallback) {
    try {
      return Integer.parseInt(properties.getProperty(string).trim());
    } catch (Exception exception) {
      // ---
    }
    return fallback;
  }

  public long getLong(String string, long fallback) {
    try {
      return Long.parseLong(properties.getProperty(string).trim());
    } catch (Exception exception) {
      // ---
    }
    return fallback;
  }

  public float getFloat(String string, float fallback) {
    try {
      return Float.parseFloat(properties.getProperty(string).trim());
    } catch (Exception exception) {
      // ---
    }
    return fallback;
  }

  public double getDouble(String string, double fallback) {
    try {
      return Double.parseDouble(properties.getProperty(string).trim());
    } catch (Exception exception) {
      // ---
    }
    return fallback;
  }

  public String getString(String string, String fallback) {
    String property = properties.getProperty(string);
    return Objects.isNull(property) //
        ? fallback.trim()
        : property.trim();
  }

  public List<String> getListString(String string, String fallback, String myDelim) {
    List<String> list = new ArrayList<>();
    // TODO NOTATION very primitive parsing
    StringTokenizer stringTokenizer = new StringTokenizer(getString(string, fallback).replace("[", "").replace("]", ""), myDelim);
    while (stringTokenizer.hasMoreTokens())
      list.add(stringTokenizer.nextToken().trim());
    return list;
  }

  public List<Integer> getListInteger(String string, List<Integer> fallback) {
    List<Integer> list = new ArrayList<>();
    boolean myBoolean = containsKey(string);
    if (myBoolean) {
      // TODO NOTATION very primitive parsing
      StringTokenizer stringTokenizer = new StringTokenizer(getString(string, "").replace('[', ' ').replace(']', ' ').replace(',', ' '));
      while (stringTokenizer.hasMoreTokens()) {
        string = stringTokenizer.nextToken();
        try {
          list.add(Integer.parseInt(string));
        } catch (Exception exception) {
          myBoolean = false;
        }
      }
      if (myBoolean)
        return list;
    }
    return fallback;
  }
}
