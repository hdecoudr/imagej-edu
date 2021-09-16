import ij.ImagePlus;
import ij.gui.NewImage;
import ij.process.ImageProcessor;

public class ColorSelectProcessor {
	private ImageProcessor ip;			
	private int refColor;				
	private int distance;				

	public ColorSelectProcessor(ImageProcessor ip, int refColor, int distance){
		this.ip = ip;
		this.refColor = refColor;
		this.distance = distance;
	}
	
	public ImagePlus processSelectedColor(){
		ImagePlus imC = NewImage.createRGBImage("Selected Color", this.ip.getWidth(), this.ip.getHeight(), 1, NewImage.FILL_WHITE);
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
		
		return imC;
	}
	
	private double distance(int col1, int col2){
		return (Math.pow(PixelTools.getRed(col1) - PixelTools.getRed(col2), 2) + 
			    Math.pow(PixelTools.getGreen(col1) - PixelTools.getGreen(col2), 2) +
			    Math.pow(PixelTools.getBlue(col1) - PixelTools.getBlue(col2), 2));
	}
}
