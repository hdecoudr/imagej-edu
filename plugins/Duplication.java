import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.gui.NewImage;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Duplication implements PlugInFilter {
	private int duplFactorW;	
	private int duplFactorH;	
	
	@Override
	public void run(ImageProcessor arg0) {
		this.initGenericDialog();
		this.duplicate(arg0);
	}

	@Override
	public int setup(String arg0, ImagePlus arg1) {
		return DOES_RGB;
	}
	
	private void initGenericDialog(){
		GenericDialog gd = new GenericDialog("Duplication Factor", IJ.getInstance());
		gd.addNumericField("Factor W", 0.0, 0);
		gd.addNumericField("Factor H", 0.0, 0);
		gd.showDialog();
		
		if(gd.wasCanceled()){
			IJ.error("PlugIn cancelled");
			return;
		}
		
		this.duplFactorW = (int)gd.getNextNumber();
		this.duplFactorH = (int)gd.getNextNumber();
	}
	
	private void duplicate(ImageProcessor ip){
		int w = ip.getWidth();
		int h = ip.getHeight();
		
		ImagePlus imD = NewImage.createRGBImage("Processed Image", w * this.duplFactorW, h * this.duplFactorH, 1, NewImage.FILL_WHITE);
		ImageProcessor ipD = imD.getProcessor();
		
		int xCurr, yCurr;
		
		for(int j = 0; j < this.duplFactorH; ++j){
			yCurr = j * h;
			
			for(int i = 0; i < this.duplFactorW; ++i){
				xCurr = i * w;
										
				for(int y = yCurr; y < (yCurr + h); ++y){
					
					for(int x = xCurr ; x < (xCurr + w); ++x){
						ipD.putPixel(x, y, ip.getPixel(x - xCurr, y - yCurr));
					}
				}
			}
		}
		
		imD.setProcessor(ipD);
		imD.show();
	}
}
