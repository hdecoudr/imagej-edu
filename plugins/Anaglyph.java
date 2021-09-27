import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Anaglyph implements PlugInFilter {
    private int shift;

    @Override
    public void run(ImageProcessor arg0) {
        this.initGenericDialog();
        AnaglyphProcessor anaProc = new AnaglyphProcessor(arg0, this.shift);
        ImagePlus imRes = anaProc.anaglyph();
        imRes.show();
    }

    @Override
    public int setup(String arg0, ImagePlus arg1) {
        return DOES_RGB;
    }

    private void initGenericDialog() {
        GenericDialog gd = new GenericDialog("Shift", IJ.getInstance());
        gd.addSlider("Shift", 0.0, 20.0, 2.0);
        gd.showDialog();
        if (gd.wasCanceled()) {
            IJ.error("PlugIn cancelled");
            return;
        }

        this.shift = (int) gd.getNextNumber();
    }
}
