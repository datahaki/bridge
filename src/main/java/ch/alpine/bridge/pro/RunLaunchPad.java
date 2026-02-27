// code by jph
package ch.alpine.bridge.pro;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import ch.alpine.bridge.cgr.InstanceDiscovery;
import ch.alpine.bridge.cgr.InstanceRecord;

// TODO all windows should have titles (noise demo simplex)
// TODO redundant to Col1 ...
public class RunLaunchPad {
  public static WindowProvider create(String packageName) {
    List<InstanceRecord<RunProvider>> list = //
        InstanceDiscovery.of(packageName, RunProvider.class);
    return new WindowProvider() {
      @Override
      public Window getWindow() {
        return new RunLaunchPad(list).jFrame;
      }
    };
  }

  private final JFrame jFrame = new JFrame();
  private final JPanel jPanel = new JPanel(new GridBagLayout());
  private final GridBagConstraints gridBagConstraints = new GridBagConstraints();

  private RunLaunchPad(List<InstanceRecord<RunProvider>> list) {
    Comparator<InstanceRecord<?>> c = new Comparator<InstanceRecord<?>>() {
      @Override
      public int compare(InstanceRecord<?> o1, InstanceRecord<?> o2) {
        return o1.toString().compareTo(o2.toString());
      }
    };
    List<InstanceRecord<RunProvider>> sorted = list.stream().sorted(c).toList();
    // ---
    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridBagConstraints.gridwidth = 1; // every row consists of 1 component
    gridBagConstraints.weightx = 1; // every row may stretch to the max
    gridBagConstraints.gridx = 0; // x position of component in grid is always 0
    jPanel.setOpaque(false);
    String prev = "";
    for (InstanceRecord<RunProvider> instanceRecord : sorted) {
      String packageName = instanceRecord.packageName();
      if (!prev.equals(packageName)) {
        JLabel jLabel = new JLabel(packageName);
        Font font = jLabel.getFont().deriveFont(Font.BOLD);
        jLabel.setFont(font);
        append(jLabel);
        prev = packageName;
      }
      RunProviderType rpt = RunProviderType.getType(instanceRecord.subcls());
      JButton jButton = new JButton(rpt + " " + instanceRecord.friendly());
      jButton.setHorizontalAlignment(SwingConstants.LEFT);
      jButton.addActionListener(_ -> instanceRecord.supplier().get().runStandalone());
      append(jButton);
    }
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    JScrollPane jScrollPane = new JScrollPane(jPanel);
    jScrollPane.getVerticalScrollBar().setUnitIncrement(25);
    jFrame.setContentPane(jScrollPane);
  }

  private void append(JComponent jComponent) {
    ++gridBagConstraints.gridy; // initially -1
    jPanel.add(jComponent, gridBagConstraints);
  }
}
