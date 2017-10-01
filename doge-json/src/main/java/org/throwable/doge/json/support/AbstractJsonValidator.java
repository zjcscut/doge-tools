package org.throwable.doge.json.support;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/9/30 0:58
 */
public abstract class AbstractJsonValidator {

	protected abstract boolean validate(String value);

	public final boolean wrapValidate(String value) {
		try {
			return validate(value);
		} catch (Exception e) {
			AlertViewFactory.createExceptionAlert(e, "抛异常了，可怕", "Json校验失败，可怕，你输入的是一个烂的json!");
		}
		return false;
	}
}
