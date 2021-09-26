import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class HorizontalFlip implements PlugInFilter {
    @Override
    public void run(ImageProcessor ip) {
        this.flipH(ip);
    }

    public void flipH(ImageProcessor ip) {
        int tempPixelValue;
        for (int j = 0; j < ip.getHeight(); ++j) {
            for (int i = 0; i < ip.getWidth() / 2; ++i) {
                tempPixelValue = ip.getPixel(i, j);
                ip.putPixel(i, j, ip.getPixel(ip.getWidth() - i - 1, j));
                ip.putPixel(ip.getWidth() - i - 1, j, tempPixelValue);
            }
        }
    }

    @Override
    public int setup(String arg, ImagePlus imp) {
        return DOES_RGB;
    }
}
