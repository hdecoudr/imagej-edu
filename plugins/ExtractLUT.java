import ij.IJ;
import ij.ImagePlus;
import ij.io.SaveDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class ExtractLUT implements PlugInFilter {
    private String saveLocation;

    @Override
    public void run(ImageProcessor arg0) {
        this.initSaveDialog();
        ExtractLUTProcessor elProc = new ExtractLUTProcessor(arg0);
        elProc.extractLUT();
        elProc.serialize(this.saveLocation);
    }

    @Override
    public int setup(String arg0, ImagePlus arg1) {
        return DOES_ALL;
    }

    private void initSaveDialog() {
        SaveDialog sd = new SaveDialog("Save LUT ...", null, null);
        if (sd.getFileName() == null) {
            IJ.error("Plugin cancelled");
            return;
        }

        this.saveLocation = sd.getDirectory() + sd.getFileName();
    }
}
