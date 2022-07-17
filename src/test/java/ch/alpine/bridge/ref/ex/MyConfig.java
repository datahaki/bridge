// code by jph
package ch.alpine.bridge.ref.ex;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.time.LocalTime;

public class MyConfig {
  public String text = "abc";
  public Boolean flag = false;
  public File file = new File("/home/user/text.txt");
  public LocalTime localTime = LocalTime.now();
  public Font font = new Font(Font.DIALOG, Font.BOLD, 14);
  public Color color = Color.RED;
}
