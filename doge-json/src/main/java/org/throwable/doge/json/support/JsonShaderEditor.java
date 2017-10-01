package org.throwable.doge.json.support;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.throwable.doge.json.common.Css.*;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/9/30 23:31
 */
public abstract class JsonShaderEditor {

	//1
	private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");
	//2
	private static final Pattern JSON_KEY_PATTERN = Pattern.compile("\"\\w+\":");  //key + ":"
	//3
	private static final Pattern COLON_PATTERN = Pattern.compile("\\:");  // ":"
	//4
	private static final Pattern LEFT_BRACKET_PATTERN = Pattern.compile("\\{");
	//5
	private static final Pattern RIGHT_BRACKET_PATTERN = Pattern.compile("\\}");
	//6
	private static final Pattern LEFT_MIDDLE_BRACKET_PATTERN = Pattern.compile("\\[");
	//7
	private static final Pattern RIGHT_MIDDLE_BRACKET_PATTERN = Pattern.compile("\\]");

	private static final Map<Pattern, String> PATTERN_FILL_COLOR_MAPPINGS = new ConcurrentHashMap<>();

	static {
		PATTERN_FILL_COLOR_MAPPINGS.putIfAbsent(NUMBER_PATTERN, FILL_GREEN);
		PATTERN_FILL_COLOR_MAPPINGS.putIfAbsent(JSON_KEY_PATTERN, FILL_BLUE);
		PATTERN_FILL_COLOR_MAPPINGS.putIfAbsent(COLON_PATTERN, FILL_PURPLE);
		PATTERN_FILL_COLOR_MAPPINGS.putIfAbsent(LEFT_BRACKET_PATTERN, FILL_RED);
		PATTERN_FILL_COLOR_MAPPINGS.putIfAbsent(RIGHT_BRACKET_PATTERN, FILL_RED);
		PATTERN_FILL_COLOR_MAPPINGS.putIfAbsent(LEFT_MIDDLE_BRACKET_PATTERN, FILL_RED);
		PATTERN_FILL_COLOR_MAPPINGS.putIfAbsent(RIGHT_MIDDLE_BRACKET_PATTERN, FILL_RED);
	}

	public static List<ShaderColorResult> shadeColorForJson(String json) {
		List<ShaderColorResult> shaderColorResults = new LinkedList<>();
		if (!json.isEmpty()) {
			for (Map.Entry<Pattern, String> entry : PATTERN_FILL_COLOR_MAPPINGS.entrySet()) {
				Pattern pattern = entry.getKey();
				Matcher matcher = pattern.matcher(json);
				while (matcher.find()) {
					shaderColorResults.add(new ShaderColorResult(matcher.start(), matcher.end(), entry.getValue()));
				}
			}
		}
		return shaderColorResults;
	}

	public static class ShaderColorResult {

		private int from;
		private int to;
		private String fillColor;

		public ShaderColorResult(int from, int to, String fillColor) {
			this.from = from;
			this.to = to;
			this.fillColor = fillColor;
		}

		public int getFrom() {
			return from;
		}

		public int getTo() {
			return to;
		}

		public String getFillColor() {
			return fillColor;
		}
	}
}
