// code by jph, gjoel
package ch.alpine.bridge.ref;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ch.alpine.bridge.ref.util.PanelFieldsEditor;
import ch.alpine.bridge.swing.LookAndFeels;

public enum PartialDemo {
  ;
  public static void main(String[] args) throws Exception {
    LookAndFeels.INTELLI_J.updateUI();
    GuiExtension guiExtension = new GuiExtension();
    PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(guiExtension);
    panelFieldsEditor.addUniversalListener(() -> System.out.println("changed"));
    ObjectPropertiesArea objectPropertiesArea = new ObjectPropertiesArea(panelFieldsEditor, guiExtension);
    // ---
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        guiExtension.string = "" + LocalDateTime.now();
        panelFieldsEditor.updateJComponents();
      }
    }, 1000, 1000);
    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    JPanel jPanel = new JPanel(new BorderLayout());
    JPanel jGrid = new JPanel(new GridLayout(2, 1));
    jGrid.add(panelFieldsEditor.createJScrollPane());
    jGrid.add(objectPropertiesArea.getJComponent());
    jPanel.add(BorderLayout.CENTER, jGrid);
    {
      JButton jButton = new JButton("reset fuse");
      jButton.addActionListener(l -> {
        guiExtension.fuse = false;
        objectPropertiesArea.run();
      });
      jPanel.add(BorderLayout.SOUTH, jButton);
    }
    jFrame.setContentPane(jPanel);
    jFrame.setBounds(500, 200, 500, 700);
    jFrame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosed(WindowEvent e) {
        timer.cancel();
      }
    });
    jFrame.setVisible(true);
  }
}
