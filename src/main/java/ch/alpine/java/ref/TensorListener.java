// code by jph
package ch.alpine.java.ref;

import java.awt.event.ActionListener;

import ch.alpine.tensor.Tensor;

/** Design rationale:
 * 
 * {@link ActionListener#actionPerformed(java.awt.event.ActionEvent)} */
public interface TensorListener {
  /** @param tensor */
  void tensorReceived(Tensor tensor);
}
