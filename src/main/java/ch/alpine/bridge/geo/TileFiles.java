// code by jph
package ch.alpine.bridge.geo;

import java.nio.file.Path;
import java.util.HexFormat;

enum TileFiles {
  ;
  private static final HexFormat HEX_FORMAT = HexFormat.of();

  public static String filename(Tile tile) {
    return String.format("%d_%d_%d.png", tile.z(), tile.x(), tile.y());
  }

  public static Path path(Path root, Tile tile) {
    final String string = filename(tile);
    final short hash = (short) string.hashCode();
    final String hexx = HEX_FORMAT.toHexDigits(hash);
    return root.resolve(hexx.substring(0, 2), hexx.substring(2, 4), string);
  }
  // static void main() throws IOException {
  // Path root = TileServers.OpenStreetMap.path();
  // Files.find(root, 3, (_, bfa) -> !bfa.isDirectory()) //
  // .forEach(source -> {
  // String name = source.getFileName().toString();
  // int s0 = name.indexOf('_');
  // int s1 = name.indexOf('_', s0 + 1);
  // int s2 = name.indexOf('.', s1 + 1);
  // String z = name.substring(0, s0);
  // String x = name.substring(s0 + 1, s1);
  // String y = name.substring(s1 + 1, s2);
  // Tile tile = new Tile( //
  // Integer.parseInt(z), //
  // Integer.parseInt(x), //
  // Integer.parseInt(y));
  // if (!filename(tile).equals(name))
  // IO.println("ERROR" + name);
  // Path target = path(tile);
  // try {
  // Files.createDirectories(target.getParent());
  // } catch (IOException e) {
  // throw new UncheckedIOException(e);
  // }
  // try {
  // Files.move(source, target);
  // } catch (IOException e) {
  // throw new UncheckedIOException(e);
  // }
  // IO.println(target);
  // });
  // }
}
