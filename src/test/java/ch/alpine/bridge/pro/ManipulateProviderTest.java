// code by jph
package ch.alpine.bridge.pro;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.ann.ReflectionMarker;

@ReflectionMarker
class ManipulateProviderTest implements ManipulateProvider {
  @Override
  public Container getContainer() {
    return new JLabel();
  }

  @Test
  void test() {
    JFrame jFrame = new ManipulateProviderTest().runStandalone();
    jFrame.setVisible(false);
  }
}
