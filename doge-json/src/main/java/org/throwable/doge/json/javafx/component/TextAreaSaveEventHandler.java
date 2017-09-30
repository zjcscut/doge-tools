package org.throwable.doge.json.javafx.component;

import javafx.scene.control.TextArea;
import org.throwable.doge.json.support.FileChooserHelper;
import org.throwable.doge.json.support.JavafxKeyCombinationFactory;
import org.throwable.doge.json.support.KeyCombinationPressedEventHandler;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/9/29 23:41
 */
public class TextAreaSaveEventHandler implements KeyCombinationPressedEventHandler {

	private final TextArea textArea;

	public TextAreaSaveEventHandler(TextArea textArea) {
		this.textArea = textArea;
	}

	@Override
	public void handle() {
		FileChooserHelper.processSaveFileByTextContent(textArea.getText());
	}

	@Override
	public void registerEvent() {
		textArea.setOnKeyPressed(JavafxKeyCombinationFactory::process);
	}
}
