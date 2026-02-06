// code by jph
package ch.alpine.bridge.fig;

import java.awt.Dimension;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import ch.alpine.tensor.ext.HomeDirectory;

/** referenced in README.md */
enum Showcase {
  ;
  static void main() throws IOException {
    Path folder = HomeDirectory.Pictures.resolve("Showcase");
    Files.createDirectories(folder);
    Set<Showcases> set = new HashSet<>();
    set.add(Showcases.ReImPlot0);
    set.add(Showcases.SpectrogramLin);
    set.add(Showcases.Cepstrogram0Re);
    set.add(Showcases.TruncatedDistribution0);
    set.add(Showcases.MatrixPlot1);
    for (Showcases showDemos : set) {
      Show show = showDemos.getShow();
      show.export(folder.resolve(showDemos.name() + ".png"), new Dimension(480, 240));
    }
  }
}
