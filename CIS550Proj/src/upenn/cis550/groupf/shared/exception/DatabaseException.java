package upenn.cis550.groupf.shared.exception;

public class DatabaseException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DatabaseException(String err) {
		super(err);
	}
	
	public DatabaseException() {
		super();
	}
}
