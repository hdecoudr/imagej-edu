import java.util.ArrayList;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.NewImage;
import ij.process.ImageProcessor;

public class ColorSelectMouseSingleImageProcessor {
    private static ColorSelectMouseSingleImageProcessor selectProcessor;
    private static class _Attr_ {
        private static ArrayList<Integer> refColors;
        private static int pSize;
    }

    private ImagePlus image;
    private ImageProcessor ip;
    private int distance;

    public static class CSMSPInstance {
        public static ColorSelectMouseSingleImageProcessor getInstance(ImageProcessor ip, int refColor, int distance) {
            if (ColorSelectMouseSingleImageProcessor.selectProcessor == null) {
                ColorSelectMouseSingleImageProcessor.selectProcessor = new ColorSelectMouseSingleImageProcessor(ip, distance);
            }

            _Attr_.pSize = _Attr_.refColors.size();
            if (!_Attr_.refColors.contains(refColor)) {
                _Attr_.refColors.add(refColor);
            }

            return ColorSelectMouseSingleImageProcessor.selectProcessor;
        }
    }

    private ColorSelectMouseSingleImageProcessor(ImageProcessor ip, int distance) {
        this.ip = ip;
        this.distance = distance;
        this.image = NewImage.createRGBImage("Selected Color", this.ip.getWidth(), this.ip.getHeight(), 1, NewImage.FILL_WHITE);
        _Attr_.refColors = new ArrayList<Integer>();
    }

    public ImagePlus processSelectedColor() {
        if (_Attr_.pSize == _Attr_.refColors.size()) {
            IJ.log("This color has already been processed !");
            return this.image;
        }

        ImageProcessor ipC = this.image.getProcessor();
        int distValue, pxValue;
        boolean done = false;
        for (int j = 0; j < ipC.getHeight(); ++j) {
            for (int i = 0; i < ipC.getWidth(); ++i) {
                for (int n = 0; n < _Attr_.refColors.size() && !done; ++n) {
                    pxValue = this.ip.getPixel(i, j);
                    distValue = (int) this.distance(_Attr_.refColors.get(n), pxValue);
                    if (distValue <= this.distance) {
                        ipC.putPixel(i, j, pxValue);
                        done = true;
                    } else {
                        ipC.putPixel(i, j, PixelTools.pixelToGrayScale(pxValue));
                    }
                }

                done = false;
            }
        }

        this.image.setProcessor(ipC);
        return this.image;
    }

    private double distance(int col1, int col2) {
        return (Math.pow(PixelTools.getRed(col1) - PixelTools.getRed(col2), 2) +
                Math.pow(PixelTools.getGreen(col1) - PixelTools.getGreen(col2), 2) +
                Math.pow(PixelTools.getBlue(col1) - PixelTools.getBlue(col2), 2));
    }
}
