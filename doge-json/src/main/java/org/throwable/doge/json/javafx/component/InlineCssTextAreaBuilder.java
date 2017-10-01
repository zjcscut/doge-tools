package org.throwable.doge.json.javafx.component;

import org.fxmisc.richtext.InlineCssTextArea;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/10/1 11:55
 */
public abstract class InlineCssTextAreaBuilder {

	public static InlineCssTextArea createInlineCssTextArea() {
		InlineCssTextArea textArea = new InlineCssTextArea();
		textArea.setAutoScrollOnDragDesired(true);
		textArea.setStyle("-fx-font-size: 15;-fx-border-style: solid;-fx-border-width: 2px;-fx-border-color: black;");
		textArea.setEditable(true);
		return textArea;
	}
}
