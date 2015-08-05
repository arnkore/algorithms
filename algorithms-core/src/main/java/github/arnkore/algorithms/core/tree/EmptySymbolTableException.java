package github.arnkore.algorithms.core.tree;

public class EmptySymbolTableException extends RuntimeException {
	private static final long serialVersionUID = -5948192200447936172L;

	public EmptySymbolTableException() {
		super();
	}

	public EmptySymbolTableException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EmptySymbolTableException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptySymbolTableException(String message) {
		super(message);
	}

	public EmptySymbolTableException(Throwable cause) {
		super(cause);
	}
}
