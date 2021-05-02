// code by gjoel, jph
package ch.alpine.java.fig;

import java.io.IOException;

import ch.alpine.tensor.ext.HomeDirectory;

/* package */ enum PlotPackageDemo {
  ;
  public static void main(String[] args) throws IOException {
    String name = PlotPackageDemo.class.getSimpleName();
    TestHelper.cascade(HomeDirectory.Pictures(name, "labels_0"), false);
    TestHelper.cascade(HomeDirectory.Pictures(name, "labels_1"), true);
  }
}
