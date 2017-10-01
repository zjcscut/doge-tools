package org.throwable.doge.json;

import org.throwable.doge.json.support.JsonFormatHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/9/30 23:03
 */
public class Main {
	//1
	private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");
	//2
	private static final Pattern JSON_KEY_PATTERN = Pattern.compile("\"\\w+\":");  //key + ":"
	//3
	private static final Pattern LEFT_COLON_PATTERN = Pattern.compile(":");  // ":"
	//4
	private static final Pattern LEFT_BRACKET_PATTERN = Pattern.compile("\\{");
	//5
	private static final Pattern RIGHT_BRACKET_PATTERN = Pattern.compile("}");
	//6
	private static final Pattern LEFT_MIDDLE_BRACKET_PATTERN = Pattern.compile("\\[");
	//7
	private static final Pattern RIGHT_MIDDLE_BRACKET_PATTERN = Pattern.compile("]");

	public static void main(String[] args) {
		String json = "{\"1111\":\"doge\",\"value\":10086}";
		String result = JsonFormatHelper.prettyToJsonFormat(json);
		Matcher matcher = LEFT_BRACKET_PATTERN.matcher(result);
		printMatcher(matcher);
		matcher = RIGHT_BRACKET_PATTERN.matcher(result);
		printMatcher(matcher);
		matcher = NUMBER_PATTERN.matcher(result);
		printMatcher(matcher);
		matcher = JSON_KEY_PATTERN.matcher(result);
		printMatcher(matcher);
		matcher = LEFT_COLON_PATTERN.matcher(result);
		printMatcher(matcher);
	}

	private static void printMatcher(Matcher matcher){
		while (matcher.find()) {
			System.out.println(matcher.group());
			System.out.print("start:" + matcher.start());
			System.out.println(" end:" + matcher.end());
		}
	}
}
