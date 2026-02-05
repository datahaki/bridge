package showcase.gal;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.alpine.bridge.fig.ImagePlot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.ShowDialog;
import ch.alpine.bridge.fig.Showcases;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Rescale;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.ext.Jpeg;
import ch.alpine.tensor.fft.FourierDCT;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.io.Import;
import ch.alpine.tensor.sca.Floor;

public enum JpegStuff {
  ;
  static void main() throws IOException {
    List<Show> list = new ArrayList<>();
    Tensor image = Rescale.of(FourierDCT._1.matrix(64)).map(ColorDataGradients.HUE).map(Floor.FUNCTION);
    // Tensor image = RandomVariate.of(UniformDistribution.unit(30), 10, 20).map(ColorDataGradients.HUE).map(Floor.FUNCTION);
    final BufferedImage bufferedImage = ImageFormat.of(image);
    File file = HomeDirectory.Pictures("jpegStuff.jpg");
    Jpeg.put(bufferedImage, file, 1f);
    {
      Show show = new Show();
      show.add(ImagePlot.of(bufferedImage));
      list.add(show);
    }
    {
      Tensor tensor = Import.of(file);
      Show show = new Show();
      BufferedImage restore = ImageFormat.of(tensor);
      show.add(ImagePlot.of(restore));
      list.add(show);
    }
    {
      BufferedImage filter = new AffineTransformOp(new AffineTransform(), AffineTransformOp.TYPE_NEAREST_NEIGHBOR).filter( //
          bufferedImage, //
          new BufferedImage( //
              bufferedImage.getWidth(), //
              bufferedImage.getHeight(), //
              BufferedImage.TYPE_3BYTE_BGR));
      Show show = new Show();
      show.add(ImagePlot.of(filter));
      list.add(show);
    }
    {
      BufferedImage filter = new AffineTransformOp(new AffineTransform(), AffineTransformOp.TYPE_NEAREST_NEIGHBOR).filter( //
          bufferedImage, //
          new BufferedImage( //
              bufferedImage.getWidth(), //
              bufferedImage.getHeight(), //
              BufferedImage.TYPE_INT_BGR));
      Show show = new Show();
      show.add(ImagePlot.of(filter));
      list.add(show);
    }
    ShowDialog.of(list);
  }
}
