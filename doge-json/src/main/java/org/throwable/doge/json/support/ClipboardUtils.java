package org.throwable.doge.json.support;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/10/3 下午6:27
 */
public abstract class ClipboardUtils {

	public static void resetClipboardContent(String content) {
		Clipboard clipboard = Clipboard.getSystemClipboard();
		ClipboardContent clipboardContent = new ClipboardContent();
		clipboardContent.putString(content);
		clipboard.setContent(clipboardContent);
	}
}
