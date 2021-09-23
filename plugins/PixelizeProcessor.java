import ij.ImagePlus;
import ij.gui.NewImage;
import ij.process.ImageProcessor;

public class PixelizeProcessor {
	private ImageProcessor ip; 	
	
	private int tileCountW;		
	private int tileCountH;		
	private int tileSize;		
	
	private double zoomFactor;
	
	public PixelizeProcessor(ImageProcessor ip, int tileCountW, int tileSize) {
		this.ip = ip;
		this.tileCountW = tileCountW;
		this.tileSize = tileSize;
	}
	
	private ImagePlus alloca() {
		this.zoomFactor = ((double)this.tileCountW * (double)this.tileSize) / (double)this.ip.getWidth();
		this.tileCountH = (int)(((double)this.ip.getHeight() / (double)this.tileSize) * this.zoomFactor);
		
		ImagePlus imS = NewImage.createRGBImage("Processed Image", 
												(this.ip.getWidth() / this.tileCountW) * this.tileCountW, 
												(this.ip.getHeight() / this.tileCountH) * this.tileCountH, 
												1, 
												NewImage.FILL_WHITE);
		return imS;
	}
	
	public ImagePlus pixelize() {
		ImagePlus im = this.alloca();
		ImageProcessor ipS = im.getProcessor();		
		int xCurr, yCurr, thrY, thrX;	
		
		for(int j = 0; j < this.tileCountH; ++j) {
			yCurr = j * (int)((double)this.tileSize / this.zoomFactor);
			
			for(int i = 0; i < this.tileCountW; ++i) {
				xCurr = i * (int)((double)this.tileSize / this.zoomFactor);
				thrY = yCurr + (int)(this.tileSize / this.zoomFactor);
				
				for(int y = yCurr; y < thrY; ++y) {
					thrX = xCurr + (int)(this.tileSize / this.zoomFactor);
					
					for(int x = xCurr; x < thrX; ++x) {
						ipS.putPixel(x, y, this.ip.getPixel(xCurr, yCurr));
					}
				}
			}
		}

		im.setProcessor(ipS.resize((int)((double)ipS.getWidth() * this.zoomFactor), 
								   (int)((double)ipS.getHeight() * this.zoomFactor)));
		return im;
	}
}
