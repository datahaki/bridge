// code by jph, gjoel
package ch.alpine.bridge.demo.ref;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ch.alpine.bridge.pro.WindowProvider;
import ch.alpine.bridge.ref.data.GuiExtension;
import ch.alpine.bridge.ref.util.PanelFieldsEditor;

/** this demo periodically invokes updateJComponents
 * to illustrate what is the behavior of the dialog
 * elements. */
class PartialDemo implements WindowProvider {
  @Override
  public Window getWindow() {
    GuiExtension guiExtension = new GuiExtension();
    PanelFieldsEditor panelFieldsEditor = PanelFieldsEditor.splits(guiExtension);
    panelFieldsEditor.addUniversalListener(() -> System.out.println("changed"));
    ObjectPropertiesArea objectPropertiesArea = new ObjectPropertiesArea(panelFieldsEditor, guiExtension);
    // ---
    JFrame jFrame = new JFrame();
    jFrame.addWindowListener(new WindowAdapter() {
      Timer timer;

      @Override
      public void windowOpened(WindowEvent e) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
          @Override
          public void run() {
            guiExtension.function = "" + LocalDateTime.now();
            panelFieldsEditor.updateJComponents();
          }
        }, 1000, 1000);
      };

      @Override
      public void windowClosed(WindowEvent windowEvent) {
        timer.cancel();
      }
    });
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    JPanel jPanel = new JPanel(new BorderLayout());
    JPanel jGrid = new JPanel(new GridLayout(2, 1));
    jGrid.add(panelFieldsEditor.createJScrollPane());
    jGrid.add(objectPropertiesArea.createJComponent());
    jPanel.add(jGrid, BorderLayout.CENTER);
    {
      JButton jButton = new JButton("reset fuse");
      jButton.addActionListener(_ -> {
        guiExtension.fuse = false;
        objectPropertiesArea.update();
      });
      jPanel.add(jButton, BorderLayout.SOUTH);
    }
    jFrame.setContentPane(jPanel);
    return jFrame;
  }

  static void main() {
    new PartialDemo().runStandalone();
  }
}
