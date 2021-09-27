import ij.*;
import ij.plugin.filter.PlugInFilter;
import ij.process.*;
import ij.gui.*;

import java.awt.event.*;
import java.util.*;

public class ColorSelectMouseSingleImage implements PlugInFilter, MouseListener, MouseMotionListener {
    private int distance;
    private ImagePlus img;
    private ImageCanvas canvas;
    private static Vector<Integer> images = new Vector<>();

    @Override
    public void run(ImageProcessor ip) {
        this.initGenericDialog();
        Integer id = img.getID();

        if (images.contains(id)) {
            IJ.log("Already listening to this image");
            return;
        } else {
            ImageWindow win = img.getWindow();
            canvas = win.getCanvas();
            canvas.addMouseListener(this);
            canvas.addMouseMotionListener(this);
            images.addElement(id);
        }
    }

    @Override
    public int setup(String arg, ImagePlus img) {
        this.img = img;
        IJ.register(MouseListener.class);
        return (DOES_ALL + NO_CHANGES);
    }

    private void initGenericDialog() {
        GenericDialog gd = new GenericDialog("Distance Select", IJ.getInstance());
        gd.addSlider("Distance", 0.0, 0xFFFFFF, 0);
        gd.showDialog();
        if (gd.wasCanceled()) {
            IJ.error("PlugIn cancelled");
            return;
        }

        this.distance = (int) gd.getNextNumber();
    }

    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        int offscreenX = canvas.offScreenX(x);
        int offscreenY = canvas.offScreenY(y);
        ColorSelectMouseSingleImageProcessor csp = ColorSelectMouseSingleImageProcessor.CSMSPInstance.getInstance(this.img.getProcessor(),
                this.img.getProcessor().getPixel(x, y),
                this.distance);
        ImagePlus imP = csp.processSelectedColor();
        if (imP.isVisible()) {
            imP.updateImage();
        } else {
            imP.show();
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }
}
