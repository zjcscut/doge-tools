package org.throwable.doge.json;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.throwable.doge.json.common.model.JsonTableEntity;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/10/1 0:28
 */
@SuppressWarnings("unchecked")
public class TreeViewMain extends Application {

	private final ObservableList<JsonTableEntity> data = FXCollections.observableArrayList();

	@Override
	public void start(Stage primaryStage) throws Exception {
		ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/public/img/json.png")));
		ImageView node = new ImageView(new Image(getClass().getResourceAsStream("/public/img/node.png")));
		ImageView node2 = new ImageView(new Image(getClass().getResourceAsStream("/public/img/node.png")));
		ImageView node22 = new ImageView(new Image(getClass().getResourceAsStream("/public/img/spring.png")));
		imageView.setFitWidth(20);
		imageView.setFitHeight(20);
		node.setFitWidth(20);
		node.setFitHeight(20);
		node2.setFitWidth(20);
		node2.setFitHeight(20);
		node22.setFitWidth(20);
		node22.setFitHeight(20);
		TreeItem root = new TreeItem("Root", imageView);
		root.setExpanded(true);
		TreeView treeView = new TreeView(root);
		TreeItem child1 = new TreeItem("Child1", node);
		root.getChildren().add(child1);
		TreeItem child2 = new TreeItem("Child2", node2);
		TreeItem child22 = new TreeItem("Child22", node22);
		child2.getChildren().add(child22);
		root.getChildren().add(child2);
		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(treeView);
		Scene scene = new Scene(new Group());
		primaryStage.setTitle("Table View Sample");
		primaryStage.setWidth(450);
		primaryStage.setHeight(500);
		((Group) scene.getRoot()).getChildren().addAll(vbox);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
