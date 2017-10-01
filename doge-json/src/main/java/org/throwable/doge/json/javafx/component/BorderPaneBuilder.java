package org.throwable.doge.json.javafx.component;

import javafx.scene.layout.BorderPane;
import org.throwable.doge.json.common.Constants;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/10/1 11:56
 */
public abstract class BorderPaneBuilder {

	public static BorderPane createBorderPane(){
		BorderPane borderPane = new BorderPane();
		borderPane.setPrefHeight(Constants.DEFAULT_STAGE_HEIGHT);
		borderPane.setPrefWidth(Constants.DEFAULT_STAGE_WIDTH);
		borderPane.setMinWidth(Constants.DEFAULT_STAGE_MIN_WIDTH);
		return borderPane;
	}
}
