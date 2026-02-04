// code by jph
package sys.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileFilter;

import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.bridge.ref.util.ObjectProperties;
import sys.Filename;

public abstract class CommonFileChooser {
  @ReflectionMarker
  public static class FileSelection {
    public File file;
  }

  private final Collection<String> collection;

  public CommonFileChooser(Collection<String> collection) {
    this.collection = collection;
  }

  private JFileChooser createJFileChooser() {
    JFileChooser jFileChooser = new JFileChooser();
    jFileChooser.setPreferredSize(new Dimension(800, 600));
    jFileChooser.setFileFilter(new FileFilter() {
      @Override
      public String getDescription() {
        return protected_getDescription();
      }

      @Override
      public boolean accept(File file) {
        return file.isDirectory() //
            || collection.contains(new Filename(file).extension().toLowerCase());
      }
    });
    return jFileChooser;
  }

  public final void addActionListenerTo(JMenuItem jMenuItem, File resource) {
    jMenuItem.addActionListener(_ -> action(jMenuItem, resource));
  }

  public final void action(Component component, File resource) {
    JFileChooser jFileChooser = createJFileChooser();
    FileSelection fileSelection = ObjectProperties.tryLoad(new FileSelection(), resource);
    if (Objects.nonNull(fileSelection.file) && //
        fileSelection.file.isDirectory())
      jFileChooser.setCurrentDirectory(fileSelection.file);
    if (jFileChooser.showOpenDialog(component) == JFileChooser.APPROVE_OPTION) {
      fileSelection.file = jFileChooser.getCurrentDirectory();
      ObjectProperties.trySave(fileSelection, resource);
      handle(jFileChooser.getSelectedFile());
    }
  }

  abstract void handle(File file);

  abstract String protected_getDescription();

  private static CommonFileChooser of(Collection<String> collection, String description, Consumer<File> consumer) {
    return new CommonFileChooser(collection) {
      @Override
      void handle(File file) {
        consumer.accept(file);
      }

      @Override
      String protected_getDescription() {
        return description;
      }
    };
  }

  public static CommonFileChooser forMidi(Consumer<File> consumer) {
    return of(List.of("mid", "midi"), "MIDI files", consumer);
  }

  public static CommonFileChooser forWav(Consumer<File> consumer) {
    return of(List.of("wav"), "Wav files", consumer);
  }

  public static CommonFileChooser forMp3(Consumer<File> consumer) {
    return of(List.of("mp3"), "MP3 files", consumer);
  }
}
