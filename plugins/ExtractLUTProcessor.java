import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import ij.IJ;
import ij.process.ImageProcessor;

public class ExtractLUTProcessor {
	private ImageProcessor ip;	
	private byte[] lut;			
	
	public ExtractLUTProcessor(ImageProcessor ip){
		this.ip = ip;
	}
	
	public void extractLUT(){
		this.lut = this.ip.getLut().getBytes();
	}
	
	public byte[] getLUT(){
		return this.lut.clone();
	}
	
	public void setLUT(byte[] l){
		this.lut = l.clone();
	}
	
	public void serialize(String path){
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(path));
			
			for(int i = 0; i < this.lut.length - 3; i += 3) {
				pw.printf("%d %d %d \n", (int)this.lut[i] & 0xFF, (int)this.lut[i + 1] & 0xFF, (int)this.lut[i + 2] & 0xFF);
			}
			
			pw.printf("%d %d %d \n", this.lut[this.lut.length - 3] & 0xFF, this.lut[this.lut.length - 2] & 0xFF, this.lut[this.lut.length - 1] & 0xFF);
			
			pw.close();
		} catch(IOException e) {
			IJ.log(e.getMessage());
			IJ.log(e.getCause().toString());
		}
	}
}
