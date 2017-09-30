package org.throwable.doge.json.utils;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/9/30 15:01
 */
public abstract class Assert {

    public static void notNull(Object value, String message, Object... objects) {
        if (null == value) {
            throw new NullPointerException(String.format(message, objects));
        }
    }
}
