
public final class PixelTools {
	public static int getAlpha(int col){
		return (col >> 0x18);
	}
	
	public static int getRed(int col){
		return ((col >> 0x10) & 0xFF);
	}
	
	public static int getGreen(int col){
		return ((col >> 0x08) & 0xFF);
	}
	
	public static int getBlue(int col){
		return (col & 0xFF);
	}
	
	public static int pixelToGrayScale(int pixel){
		double pR, pG, pB;
		pR = (double)((pixel >> 0x10) & 0xFF);
		pG = (double)((pixel >> 0x08) & 0xFF);
		pB = (double)(pixel & 0xFF);
		pR *= 0.30;
		pG *= 0.59;
		pB *= 0.11;
		
		pR = pR + pG + pB;
		
		int gray = pixel & 0xFF000000;
		gray |= ((int)pR << 0x10);
		gray |= ((int)pR << 0x08);
		gray |= ((int)pR);
		
		return gray;
	}
}
