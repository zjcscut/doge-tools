package org.throwable.doge.json;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.throwable.doge.json.support.AlertHelper;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/9/29 21:15
 */
public class DogeJsonApplication extends Application {

	public void start(Stage primaryStage) throws Exception {
		AlertHelper.createExceptionAlert(new RuntimeException("doge"),"doge","doge");

		StackPane root = new StackPane();

		Scene scene = new Scene(root, 300, 250);
		primaryStage.setTitle("Hello World!");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
