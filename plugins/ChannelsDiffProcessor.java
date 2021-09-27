import ij.ImagePlus;
import ij.gui.NewImage;
import ij.process.ImageProcessor;

public class ChannelsDiffProcessor {
    private boolean done;
    private ImagePlus im1;
    private ImagePlus im2;
    private ImagePlus ipDiffR;
    private ImagePlus ipDiffG;
    private ImagePlus ipDiffB;

    public ChannelsDiffProcessor(ImagePlus im1, ImagePlus im2) throws ImagesSizeException {
        if (im1.getWidth() != im2.getWidth() || im1.getHeight() != im2.getHeight()) {
            throw new ImagesSizeException("Images must have the same size");
        }

        this.im1 = im1;
        this.im2 = im2;
        this.channelsDiffAlloca();
        this.done = false;
    }

    private void channelsDiffAlloca() {
        this.ipDiffR = NewImage.createByteImage("Red Channel Diff", this.im1.getWidth(), this.im1.getHeight(), 1, NewImage.FILL_BLACK);
        this.ipDiffG = NewImage.createByteImage("Green Channel Diff", this.im1.getWidth(), this.im1.getHeight(), 1, NewImage.FILL_BLACK);
        this.ipDiffB = NewImage.createByteImage("Blue Channel Diff", this.im1.getWidth(), this.im1.getHeight(), 1, NewImage.FILL_BLACK);
    }

    public void difference() {
        ImageProcessor ip1 = this.im1.getProcessor();
        ImageProcessor ip2 = this.im2.getProcessor();
        ImageProcessor diffR = this.ipDiffR.getProcessor();
        ImageProcessor diffG = this.ipDiffG.getProcessor();
        ImageProcessor diffB = this.ipDiffB.getProcessor();
        int pix1, pix2;
        for (int j = 0; j < ip1.getHeight(); ++j) {
            for (int i = 0; i < ip1.getWidth(); ++i) {
                pix1 = ip1.getPixel(i, j);
                pix2 = ip2.getPixel(i, j);
                diffR.putPixel(i, j, Math.abs(PixelTools.getRed(pix1) - PixelTools.getRed(pix2)));
                diffG.putPixel(i, j, Math.abs(PixelTools.getGreen(pix1) - PixelTools.getGreen(pix2)));
                diffB.putPixel(i, j, Math.abs(PixelTools.getBlue(pix1) - PixelTools.getBlue(pix2)));
            }
        }

        this.morpho(diffR, diffG, diffB);
        this.ipDiffR.setProcessor(diffR);
        this.ipDiffG.setProcessor(diffG);
        this.ipDiffB.setProcessor(diffB);
        this.done = true;
    }

    private void morpho(ImageProcessor... ips) {
        for (ImageProcessor ip : ips) {
            ip.erode();
            ip.dilate();
        }
    }

    public ImagePlus getDiffRed() {
        return this.ipDiffR;
    }

    public ImagePlus getDiffGreen() {
        return this.ipDiffG;
    }

    public ImagePlus getDiffBlue() {
        return this.ipDiffB;
    }

    public boolean haveBeenProcessed() {
        return this.done;
    }
}
