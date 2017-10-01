package org.throwable.doge.json;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class TableViewMain extends Application {

	private final ObservableList<JsonTableEntity> data = FXCollections.observableArrayList();

	@Override
	public void start(Stage primaryStage) throws Exception {
		TableView<JsonTableEntity> tableView = new TableView<>();
		tableView.setPrefWidth(300);
		TableColumn columnFirst = new TableColumn("Key");
		columnFirst.setCellValueFactory(new PropertyValueFactory<>("key"));
		columnFirst.setMinWidth(150);
		TableColumn columnSecond = new TableColumn("Value");
		columnSecond.setCellValueFactory(new PropertyValueFactory<>("value"));
		columnSecond.setMinWidth(150);
		tableView.getColumns().addAll(columnFirst, columnSecond);
		tableView.setItems(data);
		Button button = new Button("add");
		button.setOnAction(event -> {
			data.setAll(new JsonTableEntity("name", "doge","sss"),
					new JsonTableEntity("value", "10086","ss"));
		});
		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(tableView, button);
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
