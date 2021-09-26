import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.gui.NewImage;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class ScaleImage implements PlugInFilter {
    private int width;
    private int height;
    private double factor;

    @Override
    public void run(ImageProcessor ip) {
        this.initGenericDialog();
        this.processImage(ip);
    }

    @Override
    public int setup(String arg, ImagePlus imp) {
        return DOES_RGB;
    }

    private void initGenericDialog() {
        GenericDialog gd = new GenericDialog("Scale Factor", IJ.getInstance());
        gd.addNumericField("Width", 0.0, 0);
        gd.addNumericField("Height", 0.0, 0);
        gd.addSlider("Factor", 0.0, 10.0, 2.0);
        gd.showDialog();
        if (gd.wasCanceled()) {
            IJ.error("Plugin cancelled");
            return;
        }

        this.width = (int) gd.getNextNumber();
        this.height = (int) gd.getNextNumber();
        this.factor = gd.getNextNumber();
    }

    private void processImage(ImageProcessor ip) {
        ImagePlus imS = NewImage.createRGBImage("Processed Image", this.width, this.height, 1, NewImage.FILL_WHITE);
        ImageProcessor ipS = imS.getProcessor();
        for (int j = 0; j < this.height; ++j) {
            for (int i = 0; i < this.width; ++i) {
                if (ip.getWidth() < this.width && ip.getHeight() < this.height) {
                    ipS.putPixel(i, j, ip.getPixel((int) ((double) i / this.factor),
                            (int) ((double) j / this.factor)));
                } else {
                    ipS.putPixel(i, j, ip.getPixel((int) ((double) i * this.factor),
                            (int) ((double) j * this.factor)));
                }
            }
        }

        imS.setProcessor(ipS);
        imS.show();
    }
}
