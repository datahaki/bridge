// code by jph, gjoel
package ch.alpine.java.ref;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ch.alpine.java.ref.util.PanelFieldsEditor;
import ch.alpine.javax.swing.LookAndFeels;

public enum PartialDemo {
  ;
  public static void main(String[] args) throws Exception {
    LookAndFeels.INTELLI_J.updateUI();
    GuiExtension guiExtension = new GuiExtension();
    PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(guiExtension);
    panelFieldsEditor.addUniversalListener(() -> System.out.println("changed"));
    TestHelper testHelper = new TestHelper(panelFieldsEditor, guiExtension);
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
    jPanel.add(BorderLayout.CENTER, testHelper.jPanel);
    {
      JButton jButton = new JButton("reset fuse");
      jButton.addActionListener(l -> {
        guiExtension.fuse = false;
        testHelper.runnable.run();
        // fieldsEditor.list().forEach(fp->fp.notifyListeners(""));
      });
      jPanel.add("South", jButton);
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
