package github.arnkore.algorithms.core.exception;

public class EmptyStackException extends RuntimeException {
	private static final long serialVersionUID = -6675352941515824756L;

	public EmptyStackException() {
		super();
	}

	public EmptyStackException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EmptyStackException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptyStackException(String message) {
		super(message);
	}

	public EmptyStackException(Throwable cause) {
		super(cause);
	}
}
