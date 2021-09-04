// code by jph
package ch.alpine.java.ref.obj;

import java.awt.Color;
import java.io.File;

import ch.alpine.tensor.ext.HomeDirectory;

public class AnotherParam {
  public File file = HomeDirectory.file();
  public Color color = Color.RED;
}
