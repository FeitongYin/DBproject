package upenn.cis550.groupf.shared.exception;

public class ObjectInitException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ObjectInitException(String err) {
		super(err);
	}
	
	public ObjectInitException() {
		super();
	}
}
