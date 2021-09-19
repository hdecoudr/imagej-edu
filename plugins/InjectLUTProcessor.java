import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.NewImage;
import ij.process.ImageProcessor;
import ij.process.LUT;

public class InjectLUTProcessor {
	private ImageProcessor ip;	
	private byte[] lut;		
	
	public InjectLUTProcessor(ImageProcessor ip) {
		this.ip = ip;
		this.lut = new byte[this.ip.getLut().getBytes().length];
	}
	
	public ImagePlus injectLUT(String path){
		ImagePlus imL = NewImage.createImage("LUT Injection", this.ip.getWidth(), this.ip.getHeight(), ip.getSliceNumber(), ip.getBitDepth(), 0);
		ImageProcessor ipL = (ImageProcessor)this.ip.clone();
		
		this.loadLUT(path);		
		byte[][] rgb = ArrayTools.explodeRGBChannels(this.lut);
		
		LUT l = new LUT(rgb[0], rgb[1], rgb[2]);
		ipL.setLut(l);
		
		imL.setProcessor(ipL);
		
		return imL;
	}
	
	private void loadLUT(String path) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			
			String str;
			StringTokenizer strTok;
			
			int i = 0;
			
			while((str = br.readLine()) != null) {
				strTok = new StringTokenizer(str);
				
				while(strTok.hasMoreTokens()) {
					this.lut[i] = (byte)Integer.parseInt(strTok.nextToken());
					i++;
				}
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			IJ.log(e.getMessage());
			IJ.log(e.getCause().toString());
		} catch (IOException e) {
			IJ.log(e.getMessage());
			IJ.log(e.getCause().toString());
		}
	}
}
