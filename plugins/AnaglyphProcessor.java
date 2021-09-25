import ij.ImagePlus;
import ij.gui.NewImage;
import ij.process.ImageProcessor;

public class AnaglyphProcessor {
	private ImageProcessor ip; 
	private int shift; 
	
	public AnaglyphProcessor(ImageProcessor ip, int shift) {
		this.ip = ip;
		this.shift = shift;
	}
	
	private ImagePlus alloca() {
		ImagePlus im = NewImage.createImage(
			"Anaglyph", 
			this.ip.getWidth(), 
			this.ip.getHeight(), 
			this.ip.getSliceNumber(), 
			this.ip.getBitDepth(), NewImage.FILL_BLACK
		);
		
		return im;
	}
	
	public ImagePlus anaglyph() {
		ImagePlus im = this.alloca();
		ImageProcessor ipA = im.getProcessor();
		
		for(int j = this.shift; j < (this.ip.getHeight() - this.shift); ++j) {
			for(int i = this.shift; i < (this.ip.getWidth() - this.shift); ++i) {
				ipA.putPixel(i, j, PixelTools.getRed(this.ip.getPixel(i - this.shift, j)) << 0x10 |
								   PixelTools.getGreen(this.ip.getPixel(i + this.shift, j)) << 0x08 |
								   PixelTools.getBlue(this.ip.getPixel(i + this.shift, j)));
			}
		}
		
		im.setProcessor(ipA);
		return im;
	}
}
