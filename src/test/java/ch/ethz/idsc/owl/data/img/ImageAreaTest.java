// code by jph
package ch.ethz.idsc.owl.data.img;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import ch.ethz.idsc.owl.bot.util.RegionRenders;
import ch.ethz.idsc.owl.data.Stopwatch;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class ImageAreaTest extends TestCase {
  public void testSimple() {
    BufferedImage bufferedImage = RegionRenders.image(Tensors.fromString("{{1,0,1}}"));
    // Tensor s = ImageFormat.from(bufferedImage);
    // System.out.println(s);
    // TODO Jan figure out why 244 ends up being the color value here
    Area area = ImageArea.fromImage(bufferedImage, new Color(244, 244, 244), 5);
    // System.out.println(area);
    assertTrue(area.contains(new Point2D.Double(.5, .5)));
    assertFalse(area.contains(new Point2D.Double(1.5, .5)));
    assertTrue(area.contains(new Point2D.Double(2.5, .5)));
  }

  public void testFail() {
    try {
      ImageArea.fromImage(null, Color.BLACK, 2);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testBlackWhite() throws IOException {
    BufferedImage bufferedImage = image("/dubilab/obstacles/20180423.png");
    Stopwatch stopwatch = Stopwatch.started();
    Area area = ImageArea.fromImage(bufferedImage);
    System.out.println(stopwatch.display_seconds());
    Rectangle rectangle = area.getBounds();
    System.out.println(rectangle);
  }

  private static BufferedImage image(String string) throws IOException {
    try (InputStream inputStream = ImageAreaTest.class.getResourceAsStream(string)) { // auto closeable
      return ImageIO.read(inputStream);
    }
  }
}
