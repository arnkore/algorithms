package github.arnkore.algorithms.core.queue;

public class EmptyQueueException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EmptyQueueException() {}

	public EmptyQueueException(String message) {
		super(message);
	}

	public EmptyQueueException(Throwable cause) {
		super(cause);
	}

	public EmptyQueueException(String message, Throwable cause) {
		super(message, cause);
	}
}
