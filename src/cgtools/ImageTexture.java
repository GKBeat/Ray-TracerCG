/**
 * @author henrik.tramberend@beuth-hochschule.de
 */

package cgtools;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import static cgtools.Vector.*;
import static cgtools.Color.*;

public class ImageTexture implements Sampler {
    private BufferedImage image;
    public final int width;
    public final int height;
    private final double componentScale;
    private final int components;

    public ImageTexture(String filename) throws IOException {
        image = ImageIO.read(new File(filename));
        width = image.getWidth();
        height = image.getHeight();
        components = image.getRaster().getNumBands();

        switch (image.getSampleModel().getDataType()) {
        case DataBuffer.TYPE_BYTE:
            componentScale = 255;
            break;
        case DataBuffer.TYPE_USHORT:
            componentScale = 65535;
            break;
        default:
            componentScale = 1;
            break;
        }
    }
    
    public Color getColor(double u, double v) {
        int x = (int) ((u - Math.floor(u)) * width);
        int y = (int) ((v - Math.floor(v)) * height);
        double[] pixelBuffer = new double[components];
        image.getRaster().getPixel(x, y, pixelBuffer);
        Color color = red;
        switch (components) {
        case 1:
            color = color(gamma(pixelBuffer[0]), 0, 0);
        case 2:
            color = color(gamma(pixelBuffer[0]), gamma(pixelBuffer[1]), 0);
        case 3:
            color = color(gamma(pixelBuffer[0]), gamma(pixelBuffer[1]), gamma(pixelBuffer[2]));
        case 4:
            color = color(gamma(pixelBuffer[0]), gamma(pixelBuffer[1]), gamma(pixelBuffer[2]));
        }
        return divide(color, componentScale);
    }
    
    private double gamma (double value) {
    	return Math.pow((value/componentScale), 2.2)*componentScale;
    }
}
