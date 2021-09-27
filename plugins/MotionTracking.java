import java.io.File;

import javax.swing.JFileChooser;

import ij.IJ;
import ij.ImagePlus;
import ij.Prefs;
import ij.gui.GenericDialog;
import ij.io.OpenDialog;
import ij.io.Opener;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class MotionTracking implements PlugIn {
    private ImagePlus im1;
    private ImagePlus im2;
    private ChannelsDiffProcessor chDiffProc;
    private MergeImageProcessor imMergeProc;
    private int thrM;

    @Override
    public void run(String arg0) {
        this.initDialog();
        this.openFiles();
        if (this.im1 == null || this.im2 == null) {
            return;
        }

        try {
            this.chDiffProc = new ChannelsDiffProcessor(this.im1, this.im2);
        } catch (ImagesSizeException e) {
            IJ.log(e.getMessage());
            return;
        }

        this.chDiffProc.difference();
        this.chDiffProc.getDiffRed();
        this.imMergeProc = new MergeImageProcessor(this.chDiffProc);
        ImagePlus imMerge = this.thr(this.imMergeProc.merge(MergeImageProcessor.Type.TYPE_MIN));
        imMerge.show();
    }

    private ImagePlus thr(ImagePlus im) {
        ImageProcessor ip = im.getProcessor();
        ip.threshold(this.thrM);
        im.setProcessor(ip);
        return im;
    }


    private void openFiles() {
        JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(true);
        File dir = null;
        String sDir = OpenDialog.getDefaultDirectory();
        if (sDir != null) {
            dir = new File(sDir);
        }

        if (dir != null) {
            fc.setCurrentDirectory(dir);
        }

        int returnVal = fc.showOpenDialog(IJ.getInstance());
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            IJ.error("Plugin canceled !");
            return;
        }

        File[] files = fc.getSelectedFiles();
        if (files.length != 2) {
            IJ.log("At least 2 files must be selected !");
            return;
        }

        String folderPath = fc.getCurrentDirectory().getPath() + Prefs.getFileSeparator();
        dir = fc.getCurrentDirectory();
        Opener opener = new Opener();
        im1 = opener.openImage(folderPath, files[0].getName());
        im2 = opener.openImage(folderPath, files[1].getName());
    }

    private void initDialog() {
        GenericDialog gd = new GenericDialog("Threshold min", IJ.getInstance());
        gd.addSlider("Thr", 0.0, 255.0, 0.0);
        gd.showDialog();
        if (gd.wasCanceled()) {
            IJ.error("PlugIn cancelled");
            return;
        }

        this.thrM = (int) gd.getNextNumber();
    }
}
