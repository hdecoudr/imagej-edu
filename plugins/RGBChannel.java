import ij.ImagePlus;
import ij.gui.NewImage;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class RGBChannel implements PlugInFilter {
	
	@Override
	public void run(ImageProcessor ip){
		this.processRedChannelOnly(ip);
	}
	
	private void processRedChannelOnly(ImageProcessor ip){
		int w = ip.getWidth();
		int h = ip.getHeight();
		
		ImagePlus imR = NewImage.createRGBImage ("Red canal", w, h, 1, NewImage.FILL_BLACK);
		
		ImageProcessor ipR = imR.getProcessor();
		
		for(int j = 0; j < h; ++j){
			for(int i = 0; i < w; ++i){
				ipR.putPixel(i, j, ip.getPixel(i, j) & 0xFFFF0000);
			}
		}
		
		imR.setProcessor(ipR);
		imR.show();
	}
	
	@Override
	public int setup(String arg, ImagePlus imp){
		return DOES_RGB;
	}
}
