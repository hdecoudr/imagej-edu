import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.gui.NewImage;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Mosaic implements PlugInFilter{
	private int mosaicFactor; 
	
	@Override
	public void run(ImageProcessor arg0){
		this.initGenericDialog();
		this.processImage(arg0);
	}

	@Override
	public int setup(String arg0, ImagePlus arg1){
		return DOES_RGB;
	}
	
	private void initGenericDialog(){
		GenericDialog gd = new GenericDialog("Mosaic Factor", IJ.getInstance());
		gd.addNumericField("Factor (px)", 0.0, 0);
		gd.showDialog();
		
		if(gd.wasCanceled()){
			IJ.error("PlugIn cancelled");
			return;
		}
		
		this.mosaicFactor = (int)gd.getNextNumber();
	}
	
	private void processImage(ImageProcessor ip){
		int w = ip.getWidth();
		int h = ip.getHeight();
		
		int tileWCount = w / this.mosaicFactor;
		int tileHCount = h / this.mosaicFactor;
		
		ImagePlus imS = NewImage.createRGBImage("Processed Image", w, h, 1, NewImage.FILL_WHITE);
		ImageProcessor ipS = imS.getProcessor();
		
		int xCurr;
		int yCurr;
		
		for(int j = 0; j < tileHCount; ++j){
			yCurr = j * this.mosaicFactor;
			
			for(int i = 0; i < tileWCount; ++i){
				xCurr = i * this.mosaicFactor;
				
				for(int y = yCurr; y < (yCurr + this.mosaicFactor); ++y){
					for(int x = xCurr; x < (xCurr + this.mosaicFactor); ++x){
						ipS.putPixel(x, y, ip.getPixel(i * this.mosaicFactor, j * this.mosaicFactor));
					}
				}
			}
		}
		
		imS.setProcessor(ipS);
		imS.show();
	}
}
