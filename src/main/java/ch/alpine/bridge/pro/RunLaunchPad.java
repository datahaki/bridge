// code by jph
package ch.alpine.bridge.pro;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Window;
import java.util.Comparator;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import ch.alpine.bridge.awt.ColumnPanel;
import ch.alpine.bridge.cgr.InstanceDiscovery;
import ch.alpine.bridge.cgr.InstanceRecord;
import ch.alpine.tensor.img.ColorDataLists;

// TODO all windows should have titles (noise demo simplex)
public class RunLaunchPad {
  public static WindowProvider create(String packageName) {
    List<InstanceRecord<RunProvider>> list = //
        InstanceDiscovery.of(packageName, RunProvider.class);
    return new WindowProvider() {
      @Override
      public Window getWindow() {
        return new RunLaunchPad(packageName, list.stream().sorted(COMPARATOR).toList()).jFrame;
      }
    };
  }

  private static final Comparator<InstanceRecord<?>> COMPARATOR = //
      new Comparator<InstanceRecord<?>>() {
        @Override
        public int compare(InstanceRecord<?> ir1, InstanceRecord<?> ir2) {
          return ir1.toString().compareTo(ir2.toString());
        }
      };
  // ---
  private final JFrame jFrame = new JFrame();

  private RunLaunchPad(String rootName, List<InstanceRecord<RunProvider>> list) {
    final int length = rootName.length();
    String prev = rootName;
    ColumnPanel columnPanel = new ColumnPanel();
    for (InstanceRecord<RunProvider> instanceRecord : list) {
      String packageName = instanceRecord.packageName();
      if (!prev.equals(packageName)) {
        JLabel jLabel = new JLabel(packageName.substring(length).replace(".", " "));
        Font font = jLabel.getFont().deriveFont(Font.BOLD);
        jLabel.setFont(font);
        columnPanel.add(jLabel);
        prev = packageName;
      }
      RunProviderType rpt = RunProviderType.getType(instanceRecord.subcls());
      Icon icon = SolidIcon.create(ColorDataLists._110.strict().getColor(rpt.ordinal()), 18);
      JButton jButton = new JButton(instanceRecord.friendly(), icon);
      jButton.setHorizontalAlignment(SwingConstants.LEFT);
      jButton.addActionListener(_ -> instanceRecord.supplier().get().runStandalone());
      columnPanel.add(jButton);
    }
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add(columnPanel, BorderLayout.NORTH);
    JScrollPane jScrollPane = new JScrollPane(jPanel);
    jScrollPane.getVerticalScrollBar().setUnitIncrement(25);
    jFrame.setContentPane(jScrollPane);
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    jFrame.setTitle(rootName + " [" + list.size() + "]");
  }
}
