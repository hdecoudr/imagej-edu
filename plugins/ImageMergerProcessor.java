import ij.ImagePlus;
import ij.gui.NewImage;
import ij.process.ImageProcessor;

public class ImageMergerProcessor {
	public static class Type {
		public static final int TYPE_MAX = 0;	
		public static final int TYPE_MIN = 1;	
	}

	private ChannelsDiffProcessor chDiffProc;	
	
	public ImageMergerProcessor(ChannelsDiffProcessor chDiffProc) throws ImagesNotProcessedException {
		if(!chDiffProc.haveBeenProcessed()) {
			throw new ImagesNotProcessedException();
		}
		
		this.chDiffProc = chDiffProc;
	}
	
	public ImagePlus merge(int type) {
		ImagePlus ipMerge = this.channelsMergeAlloca();
		ImageProcessor ipM = ipMerge.getProcessor();
		ImageProcessor ipDiffR = this.chDiffProc.getDiffRed().getProcessor();
		ImageProcessor ipDiffG = this.chDiffProc.getDiffGreen().getProcessor();
		ImageProcessor ipDiffB = this.chDiffProc.getDiffBlue().getProcessor();
		int pix1, pix2, pix3;		
		
		for(int j = 0; j < ipDiffR.getHeight(); ++j) {
			for(int i = 0; i < ipDiffR.getWidth(); ++i) {
				pix1 = ipDiffR.getPixel(i, j);
				pix2 = ipDiffG.getPixel(i, j);
				pix3 = ipDiffB.getPixel(i, j);
				
				switch(type) {
					case Type.TYPE_MAX: {
						ipM.putPixel(i, j, Math.max(pix1, Math.max(pix2, pix3)));
						break;
					}
					
					case Type.TYPE_MIN: {
						ipM.putPixel(i, j, Math.min(pix1, Math.min(pix2, pix3)));
						break;
					}
				}
			}
		}
		
		ipMerge.setProcessor(ipM);
		return ipMerge;
	}
	
	private ImagePlus channelsMergeAlloca() {
		ImagePlus im = NewImage.createByteImage(
									"MergeChannel",
									this.chDiffProc.getDiffRed().getWidth(), 
									this.chDiffProc.getDiffRed().getHeight(), 
									1, 
									NewImage.FILL_BLACK
								);
		
		return im;
	}
}
