package org.throwable.doge.json.exception;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/9/29 23:55
 */
public class DirectoryChooserException extends RuntimeException {

	public DirectoryChooserException(String message) {
		super(message);
	}

	public DirectoryChooserException(String message, Throwable cause) {
		super(message, cause);
	}

	public DirectoryChooserException(Throwable cause) {
		super(cause);
	}
}
