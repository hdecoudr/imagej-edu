import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class GrayScale implements PlugInFilter {
    @Override
    public void run(ImageProcessor arg0) {
        GrayScaleProcessor gsProc = new GrayScaleProcessor(arg0);
        ImagePlus gIm = gsProc.toGrayScale();
        gIm.show();
    }

    @Override
    public int setup(String arg0, ImagePlus arg1) {
        return DOES_RGB;
    }
}

