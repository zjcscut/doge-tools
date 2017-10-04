package org.throwable.doge.json.javafx.component;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.throwable.doge.json.common.Constants;
import org.throwable.doge.json.common.model.JsonTableEntity;
import org.throwable.doge.json.support.ClipboardUtils;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/10/1 12:11
 */
@SuppressWarnings("unchecked")
public abstract class TableViewBuilder {

	public static TableView<JsonTableEntity> createTableView() {
		TableView<JsonTableEntity> tableView = new TableView<>();
		tableView.setEditable(false);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); //表头宽度自适应
		tableView.setPrefWidth(Constants.DEFAULT_TABLE_VIEW_WIDTH);
		tableView.setMinWidth(Constants.DEFAULT_TABLE_VIEW_WIDTH);
		TableColumn<JsonTableEntity, String> keyColumn = new TableColumn<>("Key");
		keyColumn.setSortable(false);
		keyColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
		TableColumn<JsonTableEntity, String> valueColumn = new TableColumn<>("Value");
		valueColumn.setSortable(false);
		valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
		TableColumn<JsonTableEntity, String> typeColumn = new TableColumn<>("Type");
		typeColumn.setSortable(false);
		typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
		tableView.getColumns().addAll(keyColumn, valueColumn, typeColumn);
		registerSelectedMenuEventAction(tableView);
		return tableView;
	}

	private static void registerSelectedMenuEventAction(TableView<JsonTableEntity> tableView) {
		tableView.getSelectionModel().setCellSelectionEnabled(true);
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		ContextMenu contextMenu = new ContextMenu();
		contextMenu.setAutoHide(true);
		MenuItem copyKey = new MenuItem("Copy key");
		copyKey.setOnAction(event -> {
			ObservableList<JsonTableEntity> selectedItems = tableView.getSelectionModel().getSelectedItems();
			if (null != selectedItems && !selectedItems.isEmpty()) {
				StringBuffer key = new StringBuffer();
				selectedItems.forEach(each -> key.append(each.getKey()).append("\n"));
				ClipboardUtils.resetClipboardContent(key.substring(0, key.lastIndexOf("\n")));
			}
		});
		MenuItem copyValue = new MenuItem("Copy value");
		copyValue.setOnAction(event -> {
			ObservableList<JsonTableEntity> selectedItems = tableView.getSelectionModel().getSelectedItems();
			if (null != selectedItems && !selectedItems.isEmpty()) {
				StringBuffer value = new StringBuffer();
				selectedItems.forEach(each -> value.append(each.getValue()).append("\n"));
				ClipboardUtils.resetClipboardContent(value.substring(0, value.lastIndexOf("\n")));
			}
		});
		MenuItem copyKeyValue = new MenuItem("Copy key-value");
		copyKeyValue.setOnAction(event -> {
			ObservableList<JsonTableEntity> selectedItems = tableView.getSelectionModel().getSelectedItems();
			if (null != selectedItems && !selectedItems.isEmpty()) {
				StringBuffer keyValue = new StringBuffer();
				selectedItems.forEach(each -> {
					keyValue.append(each.getKey()).append(":");
					if (null != each.getValue()) {
						keyValue.append(each.getValue());
					}
					keyValue.append("\n");
				});
				ClipboardUtils.resetClipboardContent(keyValue.substring(0, keyValue.lastIndexOf("\n")));
			}
		});
		contextMenu.getItems().addAll(copyKey, copyValue, copyKeyValue);
		tableView.setContextMenu(contextMenu);
	}
}
