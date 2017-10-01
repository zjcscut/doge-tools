package org.throwable.doge.json.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/9/30 1:03
 */
public abstract class JsonFormatHelper {

	public static String prettyToJsonFormat(String value) {
		Object jsonObject = JSON.parse(value, Feature.AllowSingleQuotes);
		return JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue,
				SerializerFeature.PrettyFormat);
	}

	public static JSONObject parseToJsonObject(String value) {
		return JSON.parseObject(value, Feature.AllowSingleQuotes);
	}

	public static String minifyJson(String json) {
		Object jsonObject = JSON.parse(json, Feature.AllowSingleQuotes);
		return JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
	}
}
