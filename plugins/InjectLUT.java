import ij.IJ;
import ij.ImagePlus;
import ij.io.OpenDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class InjectLUT implements PlugInFilter{
	private String openLocation;	
	
	@Override
	public void run(ImageProcessor arg0) {
		this.initLoadDialog();
		InjectLUTProcessor ilProc = new InjectLUTProcessor(arg0);
		ImagePlus imL = ilProc.injectLUT(this.openLocation);
		imL.show();
	}

	@Override
	public int setup(String arg0, ImagePlus arg1) {
		return DOES_ALL;
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
