package org.throwable.doge.json.javafx;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import org.throwable.doge.json.javafx.component.BorderPaneBuilder;
import org.throwable.doge.json.javafx.component.TabPaneBuilder;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/10/1 12:24
 */
public final class ViewController {

	public Parent initViewsAndReturnRoot(){
		BorderPane borderPane = BorderPaneBuilder.createBorderPane();
		borderPane.setCenter(TabPaneBuilder.createTabPane());
		return borderPane;
	}
}
