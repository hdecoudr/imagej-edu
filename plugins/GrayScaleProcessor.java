import ij.ImagePlus;
import ij.gui.NewImage;
import ij.process.ImageProcessor;

public class GrayScaleProcessor {
	private ImageProcessor ip;		
	
	public GrayScaleProcessor(ImageProcessor ip){
		this.ip = ip;
	}

	public ImagePlus toGrayScale(){
		ImagePlus imG = NewImage.createRGBImage("Gray Scale", this.ip.getWidth(), this.ip.getHeight(), 1, NewImage.FILL_WHITE);
		ImageProcessor ipG = imG.getProcessor();
		
		for(int j = 0; j < ipG.getHeight(); ++j){
			for(int i = 0; i < ipG.getWidth(); ++i){
				ipG.putPixel(i, j, this.pixelToGrayScale(ip.getPixel(i, j)));
			}
		}
		
		imG.setProcessor(ipG);
		
		return imG;
	}
	
	private int pixelToGrayScale(int pixel){
		double pR, pG, pB;
		pR = (double)((pixel >> 0x10) & 0xFF);
		pG = (double)((pixel >> 0x08) & 0xFF);
		pB = (double)(pixel & 0xFF);
		pR *= 0.30;
		pG *= 0.59;
		pB *= 0.11;
		
		pR = pR + pG + pB;
		
		int gray = pixel & 0xFF000000;
		gray |= ((int)pR << 0x10);
		gray |= ((int)pR << 0x08);
		gray |= ((int)pR);
		
		return gray;
	}
}
