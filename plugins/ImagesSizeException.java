public class ImagesSizeException extends Exception {
	private static final long serialVersionUID = 4725765387933513203L;

	public ImagesSizeException() {
		super("Processed images must have the same size!");
	}
}
