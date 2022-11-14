// code by jph
package ch.alpine.bridge.fig;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import ch.alpine.tensor.ext.HomeDirectory;

public enum Showcase {
  ;
  public static void main(String[] args) throws IOException {
    File folder = HomeDirectory.Pictures("Showcase");
    folder.mkdir();
    Set<ShowDemos> set = new HashSet<>();
    set.add(ShowDemos.ReImPlot0);
    set.add(ShowDemos.Spectrogram0);
    set.add(ShowDemos.Cepstrogram0Re);
    set.add(ShowDemos.TruncatedDistribution0);
    set.add(ShowDemos.MatrixPlot1);
    for (ShowDemos showDemos : set) {
      Show show = showDemos.create();
      show.export(new File(folder, showDemos.name() + ".png"), new Dimension(480, 240));
    }
  }
}
