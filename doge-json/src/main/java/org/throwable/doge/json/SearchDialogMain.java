package org.throwable.doge.json;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.throwable.doge.json.common.Constants;
import org.throwable.doge.json.support.AlertViewFactory;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/10/3 下午10:59
 */
public class SearchDialogMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Stage stage = new Stage();
		stage.setResizable(false);
		DialogPane dialogPane = new DialogPane();
		VBox vBox = new VBox();
		vBox.setPrefWidth(600);
		vBox.setPrefHeight(100);
		HBox hBox = new HBox();
		hBox.setPrefWidth(600);
		hBox.setPrefHeight(50);
		hBox.setPadding(new Insets(15, 12, 15, 12)); //节点到边缘的距离
		hBox.setSpacing(10); //节点之间的间距
		Text text1 = new Text("Search Content：");
		text1.setFont(new Font(20));
		TextField textField1 = new TextField();
		textField1.setStyle("-fx-background-color: yellow");
		textField1.setPrefHeight(50);
		textField1.setPrefWidth(480);
		hBox.getChildren().addAll(text1, textField1);
		HBox hBox2 = new HBox();
		hBox2.setPrefWidth(600);
		hBox2.setPrefHeight(50);
		hBox2.setPadding(new Insets(15, 12, 15, 328)); //节点到边缘的距离
		hBox2.setSpacing(10); //节点之间的间距
		Button button1 = new Button("上一个");
		button1.setPrefWidth(80);
		Button button2 = new Button("下一个");
		button2.setPrefWidth(80);
		Button button3 = new Button("关闭");
		button3.setPrefWidth(80);
		button3.setOnAction(event -> stage.close());
		hBox2.getChildren().addAll(button1, button2, button3);
		vBox.getChildren().addAll(hBox,hBox2);
		dialogPane.setContent(vBox);
		Scene scene = new Scene(dialogPane, 600, 130);
		stage.setScene(scene);
		stage.setTitle("Search");
		stage.getIcons().add(new Image(AlertViewFactory.class.getResourceAsStream(Constants.DEFAULT_LOGO_PATH)));
		stage.show();
		StackPane pane = new StackPane();
		Scene scene2 = new Scene(pane, 600, 100);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Hello World!");
		primaryStage.setScene(scene2);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
