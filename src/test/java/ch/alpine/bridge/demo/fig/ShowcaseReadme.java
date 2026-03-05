// code by jph
package ch.alpine.bridge.demo.fig;

import java.awt.Dimension;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.ext.HomeDirectory;

/** referenced in README.md */
enum ShowcaseReadme {
  ;
  static void main() throws IOException {
    Path folder = HomeDirectory.Pictures.resolve("Showcase");
    Files.createDirectories(folder);
    Showcases[] set = new Showcases[] { //
        Showcases.ReImPlot0, //
        Showcases.SpectrogramLin, //
        Showcases.Cepstrogram0Re, //
        Showcases.TruncatedDistribution0, //
        Showcases.DensityJulia, //
        Showcases.MatrixPlot1 };
    for (Showcases showDemos : set) {
      Show show = showDemos.getShow();
      show.export(folder.resolve(showDemos.name() + ".png"), new Dimension(480, 240));
    }
  }
}
