// code by gjoel, jph
package ch.alpine.bridge.usr;

import java.io.IOException;

import ch.alpine.bridge.fig.CascadeHelper;
import ch.alpine.tensor.ext.HomeDirectory;

/* package */ enum PlotPackageDemo {
  ;
  public static void main(String[] args) throws IOException {
    String name = PlotPackageDemo.class.getSimpleName();
    CascadeHelper.cascade(HomeDirectory.Pictures(name, "labels_0"), false);
    CascadeHelper.cascade(HomeDirectory.Pictures(name, "labels_1"), true);
  }
}
