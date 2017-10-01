package org.throwable.doge.json.support;

import com.alibaba.fastjson.JSON;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/9/30 0:33
 */
public class FastJsonValidator extends AbstractJsonValidator {

	@Override
	protected boolean validate(String value) {
		JSON.parse(value);
		return true;
	}
}
