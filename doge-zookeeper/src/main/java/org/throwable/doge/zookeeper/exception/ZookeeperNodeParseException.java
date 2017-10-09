package org.throwable.doge.zookeeper.exception;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/10/9 18:30
 */
public class ZookeeperNodeParseException extends RuntimeException {

    public ZookeeperNodeParseException(String message) {
        super(message);
    }

    public ZookeeperNodeParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZookeeperNodeParseException(Throwable cause) {
        super(cause);
    }
}
