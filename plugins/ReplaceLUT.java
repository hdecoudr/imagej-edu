import ij.IJ;
import ij.ImagePlus;
import ij.io.OpenDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class ReplaceLUT implements PlugInFilter{
	private String openLocation;	
	
	@Override
	public void run(ImageProcessor arg0) {
		this.initLoadDialog();
		
		ReplaceLUTProcessor rlProc = new ReplaceLUTProcessor(arg0);
		ImagePlus ipR = rlProc.injectLUT(this.openLocation);
		ipR.show();
	}

	@Override
	public int setup(String arg0, ImagePlus arg1) {
		return DOES_RGB;
	}

	private void initLoadDialog(){
		OpenDialog od = new OpenDialog("Load LUT ...");
		
		if(od.getFileName() == null){
			IJ.error("Plugin cancelled");
			return;
		}
		
		this.openLocation = od.getPath();
	}

}
