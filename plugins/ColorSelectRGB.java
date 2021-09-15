import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.gui.NewImage;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class ColorSelectRGB implements PlugInFilter{
	private int refColor;	
	private int distance;	
	
	@Override
	public void run(ImageProcessor arg0) {
		this.initGenericDialog();
		this.processSelectedColor(arg0);
	}

	@Override
	public int setup(String arg0, ImagePlus arg1) {
		return DOES_RGB;
	}
	
	private void initGenericDialog(){
		GenericDialog gd = new GenericDialog("Color Select", IJ.getInstance());
		gd.addNumericField("R", 0.0, 0);
		gd.addNumericField("G", 0.0, 0);
		gd.addNumericField("B", 0.0, 0);
		gd.addSlider("Distance", 0.0, 0xFFFFFF, 0);
		gd.showDialog();
		
		if(gd.wasCanceled()){
			IJ.error("PlugIn cancelled");
			return;
		}
		
		this.refColor = 0xFF000000;
		this.refColor |= ((int)gd.getNextNumber() << 0x10);
		this.refColor |= ((int)gd.getNextNumber() << 0x08);
		this.refColor |= (int)gd.getNextNumber();
		this.distance = (int)gd.getNextNumber();
	}
	
	private void imageToGrayScale(ImageProcessor ip){
		ImagePlus imG = NewImage.createRGBImage("Gray Scale", ip.getWidth(), ip.getHeight(), 1, NewImage.FILL_WHITE);
		ImageProcessor ipG = imG.getProcessor();
		
		for(int j = 0; j < ipG.getHeight(); ++j){
			for(int i = 0; i < ipG.getWidth(); ++i){
				ipG.putPixel(i, j, PixelTools.pixelToGrayScale(ip.getPixel(i, j)));
			}
		}
		
		imG.setProcessor(ipG);
		imG.show();
	}
	
	private void processSelectedColor(ImageProcessor ip){
		ImagePlus imC = NewImage.createRGBImage("Selected Color", ip.getWidth(), ip.getHeight(), 1, NewImage.FILL_WHITE);
		ImageProcessor ipC = imC.getProcessor();
		
		int distValue, pxValue;
		
		for(int j = 0; j < ipC.getHeight(); ++j){
			for(int i = 0; i < ipC.getWidth(); ++i){
				pxValue = ip.getPixel(i, j);
				distValue = (int)this.distance(this.refColor, pxValue);
				
				if(distValue <= this.distance){
					ipC.putPixel(i, j, pxValue);
				}else{
					ipC.putPixel(i, j, PixelTools.pixelToGrayScale(pxValue));
				}
			}
		}
		
		imC.setProcessor(ipC);
		imC.show();
	}
	
	private double distance(int col1, int col2){
		return (Math.pow(PixelTools.getRed(col1) - PixelTools.getRed(col2), 2) + 
			    Math.pow(PixelTools.getGreen(col1) - PixelTools.getGreen(col2), 2) +
			    Math.pow(PixelTools.getBlue(col1) - PixelTools.getBlue(col2), 2));
	}
}
