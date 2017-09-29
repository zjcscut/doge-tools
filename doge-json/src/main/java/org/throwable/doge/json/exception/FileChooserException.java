package org.throwable.doge.json.exception;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/9/30 0:05
 */
public class FileChooserException extends RuntimeException {

	public FileChooserException(String message) {
		super(message);
	}

	public FileChooserException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileChooserException(Throwable cause) {
		super(cause);
	}
}
