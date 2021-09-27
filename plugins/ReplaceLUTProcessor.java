import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.NewImage;
import ij.process.ImageProcessor;

public class ReplaceLUTProcessor {
    private ImageProcessor ip;
    private byte[] lut;

    public ReplaceLUTProcessor(ImageProcessor ip) {
        this.ip = ip;
        this.lut = new byte[768];
    }

    public ImagePlus injectLUT(String path) {
        ImagePlus imR = NewImage.createImage("LUT Injection", this.ip.getWidth(), this.ip.getHeight(), this.ip.getSliceNumber(), this.ip.getBitDepth(), NewImage.FILL_WHITE);
        ImageProcessor ipR = imR.getProcessor();
        this.loadLUT(path);
        int[] rgb = ArrayTools.mergeRGBChannels(this.lut);
        this.paint(ipR, rgb);
        imR.setProcessor(ipR);
        return imR;
    }

    private void paint(ImageProcessor ip, int[] palette) {
        int palValue;
        int pixValue;
        int rgbDist;
        for (int j = 0; j < this.ip.getHeight(); ++j) {
            for (int i = 0; i < this.ip.getWidth(); ++i) {
                palValue = palette[0];
                pixValue = this.ip.getPixel(i, j) & 0x00FFFFFF;
                rgbDist = PixelTools.deltaCIE76DistanceRGB(pixValue, palValue);
                for (int n = 1; n < palette.length; ++n) {
                    if (PixelTools.deltaCIE76DistanceRGB(pixValue, palette[n]) < rgbDist) {
                        rgbDist = PixelTools.deltaCIE76DistanceRGB(pixValue, palValue);
                        palValue = palette[n];
                    }
                }

                ip.putPixel(i, j, palValue | ((this.ip.getPixel(i, j) & 0xFF000000)));
            }
        }
    }

    private void loadLUT(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String str;
            StringTokenizer strTok;
            int i = 0;
            while ((str = br.readLine()) != null) {
                strTok = new StringTokenizer(str);
                while (strTok.hasMoreTokens()) {
                    this.lut[i] = (byte) Integer.parseInt(strTok.nextToken());
                    i++;
                }
            }

            br.close();
        } catch (FileNotFoundException e) {
            IJ.log(e.getMessage());
            IJ.log(e.getCause().toString());
        } catch (IOException e) {
            IJ.log(e.getMessage());
            IJ.log(e.getCause().toString());
        }
    }
}
