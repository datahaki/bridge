// code by jph
package ch.alpine.bridge.ref;

import java.io.File;
import java.util.List;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.num.Pi;

public class MissingMarkerParam extends MissingMarkerBase {
  public final PartsParam partsParam = new PartsParam();
  public final List<EntryParam> list = List.of(new EntryParam(), new EntryParam());
  public final ArrayParam[] arrayParams = new ArrayParam[] { new ArrayParam(), new ArrayParam(), new ArrayParam() };

  public static class EntryParam extends EntryBase {
    public Boolean status = false;
  }

  public static class EntryBase {
    public Scalar value = Pi.VALUE;
  }

  public static class ArrayParam {
    public File file = HomeDirectory.file();
  }

  public static class PartsParam {
    public String text = "";
  }
}
