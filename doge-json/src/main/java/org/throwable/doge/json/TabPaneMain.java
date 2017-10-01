package org.throwable.doge.json;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.throwable.doge.json.common.model.JsonTableEntity;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/10/1 0:28
 */
@SuppressWarnings("unchecked")
public class TabPaneMain extends Application {

	private final ObservableList<JsonTableEntity> data = FXCollections.observableArrayList();

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane borderPane = new BorderPane();
		TabPane tabPane = new TabPane();
		Tab tab = new Tab();
		tab.setText("Tab-1");
		tabPane.getTabs().add(tab);
		borderPane.setCenter(tabPane);
		Scene scene = new Scene(borderPane, 300, 250);
		primaryStage.setTitle("Table View Sample");
		primaryStage.setWidth(450);
		primaryStage.setHeight(500);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
