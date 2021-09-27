import java.awt.Color;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.gui.NewImage;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class ColorSelectHSV implements PlugInFilter {
    private float[] refHSV;
    private float distance;

    @Override
    public void run(ImageProcessor arg0) {
        this.initGenericDialog();
        this.processSelectedColor(arg0);
    }

    @Override
    public int setup(String arg0, ImagePlus arg1) {
        return DOES_RGB;
    }

    private void initGenericDialog() {
        GenericDialog gd = new GenericDialog("Color Select", IJ.getInstance());
        gd.addNumericField("R", 0.0, 0);
        gd.addNumericField("G", 0.0, 0);
        gd.addNumericField("B", 0.0, 0);
        gd.addSlider("Distance", 0.0, 1.0, 0);
        gd.showDialog();
        if (gd.wasCanceled()) {
            IJ.error("PlugIn cancelled");
            return;
        }

        this.refHSV = new float[3];
        Color.RGBtoHSB((int) gd.getNextNumber(), (int) gd.getNextNumber(), (int) gd.getNextNumber(), this.refHSV);
        this.distance = (float) gd.getNextNumber();
    }

    private void processSelectedColor(ImageProcessor ip) {
        ImagePlus imC = NewImage.createRGBImage("Selected Color", ip.getWidth(), ip.getHeight(), 1, NewImage.FILL_WHITE);
        ImageProcessor ipC = imC.getProcessor();
        int pxValueRGB;
        float[] pxValueHSV = new float[3];
        for (int j = 0; j < ipC.getHeight(); ++j) {
            for (int i = 0; i < ipC.getWidth(); ++i) {
                pxValueRGB = ip.getPixel(i, j);
                Color.RGBtoHSB(PixelTools.getRed(pxValueRGB),
                        PixelTools.getGreen(pxValueRGB),
                        PixelTools.getBlue(pxValueRGB),
                        pxValueHSV);
                if ((pxValueHSV[0] <= (this.refHSV[0] + this.distance)) &&
                        (pxValueHSV[0] >= (this.refHSV[0] - this.distance))) {
                    ipC.putPixel(i, j, pxValueRGB);
                } else {
                    ipC.putPixel(i, j, PixelTools.pixelToGrayScale(pxValueRGB));
                }
            }
        }

        imC.setProcessor(ipC);
        imC.show();
    }
}
