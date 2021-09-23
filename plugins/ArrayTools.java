public final class ArrayTools {
	public static byte[][] explodeRGBChannels(byte[] rgb) {
		byte[][] expl = new byte[3][rgb.length / 3];
		int i = 0;
		int j = 0;
		
		for( ; i < (rgb.length - 3); i += 3, ++j) {
			expl[0][j] = rgb[i];
			expl[1][j] = rgb[i + 1];
			expl[2][j] = rgb[i + 2];
		}
		
		expl[0][j] = rgb[i];
		expl[1][j] = rgb[i + 1];
		expl[2][j] = rgb[i + 2];
		
		return expl;
	}
	
	public static int[] mergeRGBChannels(byte[] rgb) {
		int[] merg = new int[rgb.length / 3];
		
		int i = 0;
		int j = 0;
		
		for( ; i < (rgb.length - 3); i += 3, ++j) {
			merg[j] = (((int)rgb[i] & 0xFF) << 0x10 | ((int)rgb[i + 1] & 0xFF) << 0x08 | ((int)rgb[i + 2] & 0xFF));
		}
		
		merg[j] = (((int)rgb[i] & 0xFF) << 0x10 | ((int)rgb[i + 1] & 0xFF) << 0x08 | ((int)rgb[i + 2]) & 0xFF);
		
		return merg;
	}
	
	public static byte[] mergeRGBChannels(int[] r, int[] g, int[] b) {
		if(r.length != g.length || r.length != b.length || g.length != b.length) {
			return null;
		}
		
		byte[] merg = new byte[r.length * 3];
		
		
		int i = 0;
		int j = 0;
		
		for( ; i < (r.length - 3); ++i, j += 3) {
			merg[j] = (byte)(r[i] & 0xFF);
			merg[j + 1] = (byte)(g[i] & 0xFF);
			merg[j + 2] = (byte)(b[i] & 0xFF);
		}
		
		merg[j] = (byte)(r[i] & 0xFF);
		merg[j + 1] = (byte)(g[i] & 0xFF);
		merg[j + 2] = (byte)(b[i] & 0xFF);
		
		return merg;
	}
}
