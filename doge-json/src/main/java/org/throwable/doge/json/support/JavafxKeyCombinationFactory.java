package org.throwable.doge.json.support;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/9/29 23:27
 */
public abstract class JavafxKeyCombinationFactory {

	public static final KeyCombination CONTROL_S = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_ANY);
	public static final KeyCombination CONTROL_F = new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_ANY);
	public static final KeyCombination CONTROL_M = new KeyCodeCombination(KeyCode.M, KeyCombination.CONTROL_ANY);

	private static final Map<KeyCombination, KeyCombinationPressedEventHandler> HANDLERS_MAP = new ConcurrentHashMap<>();

	public static void register(KeyCombination keyCombination, KeyCombinationPressedEventHandler eventHandler) {
		HANDLERS_MAP.putIfAbsent(keyCombination, eventHandler);
	}

	public static void process(KeyEvent keyEvent) {
		for (Map.Entry<KeyCombination, KeyCombinationPressedEventHandler> entry : HANDLERS_MAP.entrySet()) {
			if (entry.getKey().match(keyEvent)) {
				entry.getValue().handle();
			}
		}
	}
}
