package github.arnkore.algorithms.core.common.exception;

public class EmptyHeapException extends RuntimeException {
	private static final long serialVersionUID = -1694787667057742342L;

	public EmptyHeapException() {
		super();
	}

	public EmptyHeapException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EmptyHeapException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptyHeapException(String message) {
		super(message);
	}

	public EmptyHeapException(Throwable cause) {
		super(cause);
	}
}
