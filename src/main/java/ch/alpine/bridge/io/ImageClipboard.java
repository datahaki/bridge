// code adapted from chatgpt
package ch.alpine.bridge.io;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;

public enum ImageClipboard {
  ;
  public static void copy(BufferedImage bufferedImage) {
    Transferable transferable = new ImageTransferable(bufferedImage);
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(transferable, null);
  }

  private record ImageTransferable(Image image) implements Transferable {
    @Override
    public DataFlavor[] getTransferDataFlavors() {
      return new DataFlavor[] { DataFlavor.imageFlavor };
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor dataFlavor) {
      return DataFlavor.imageFlavor.equals(dataFlavor);
    }

    @Override
    public Object getTransferData(DataFlavor dataFlavor) throws UnsupportedFlavorException {
      if (!isDataFlavorSupported(dataFlavor))
        throw new UnsupportedFlavorException(dataFlavor);
      return image;
    }
  }
}
