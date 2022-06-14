// code by jph
package ch.alpine.bridge.ref;

import java.awt.Color;

import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.io.ResourceData;

@ReflectionMarker
public class ParamContainer {
  public static final ParamContainer INSTANCE = ObjectProperties.set(new ParamContainer(), ResourceData.properties("/ch/alpine/bridge/io/ParamContainer.properties"));
  // ---
  public String string;
  public Scalar maxTor;
  public Tensor shape;
  public Scalar abc;
  public Boolean status;
  public Color foreground;
  // ---
  // ignore the following
  public transient Scalar _transient;
  /* package */ Scalar _package;
  public int nono; // int's are ignored
  public final Scalar _final = RealScalar.ONE;
  protected Boolean _protected;
  public static String _static = "string value";
}
