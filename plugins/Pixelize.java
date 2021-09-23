import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Pixelize implements PlugInFilter {
	private int tileSize;
	private int tileCountW;
	
	@Override
	public void run(ImageProcessor arg0) {
		this.initGenericDialog();
		
		PixelizeProcessor pixProc = new PixelizeProcessor(arg0, this.tileCountW, this.tileSize);
		ImagePlus ipRes = pixProc.pixelize();
		ipRes.show();
	}

	@Override
	public int setup(String arg0, ImagePlus arg1) {
		return DOES_RGB;
	}
	
	private void initGenericDialog() {
		GenericDialog gd = new GenericDialog("Pixelize", IJ.getInstance());
		gd.addSlider("Tile Size", 0.0, 200.0, 0.0);
		gd.addSlider("Tile Count (Width)", 0.0, 1000.0, 0.0);
		gd.showDialog();
		
		if(gd.wasCanceled()) {
			IJ.error("PlugIn cancelled");
			return;
		}
		
		this.tileSize = (int)gd.getNextNumber();
		this.tileCountW = (int)gd.getNextNumber();
	}
}
