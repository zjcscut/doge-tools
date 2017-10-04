package org.throwable.doge.json.support;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.throwable.doge.json.common.TreeViewItemType;
import org.throwable.doge.json.common.model.JsonTableEntity;
import org.throwable.doge.json.common.model.TreeViewEntity;
import org.throwable.doge.json.javafx.component.ImageViewBuilder;
import org.throwable.doge.json.utils.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/10/1 18:35
 */
public abstract class JsonTreeViewParser {

	private static final String ROOT = "ROOT";
	private static final String NULL = "<NULL>";

	public static void parseJsonObjectToTreeViewRootItem(TreeView<TreeViewEntity> treeView,
														 JSONObject jsonObject,
														 TableView<JsonTableEntity> tableView) {
		if (null != jsonObject) {
			TreeViewEntity rootEntity = new TreeViewEntity(ROOT, ROOT, TreeViewItemType.ROOT);
			ObservableList<JsonTableEntity> rootTableEntity = FXCollections.observableArrayList();
			TreeItem<TreeViewEntity> root = new TreeItem<>(rootEntity, ImageViewBuilder.createTreeViewRootIcon());
			analyzeJsonObject(root, jsonObject, rootTableEntity);
			treeView.setRoot(root);
			//添加鼠标左击的时候复制键,右击时候复制值
			EventHandler<MouseEvent> mouseClickEventHandler = event -> {
				if (MouseEvent.MOUSE_CLICKED.equals(event.getEventType())) {
					TreeItem<TreeViewEntity> treeItem = treeView.getSelectionModel().getSelectedItem();
					if (null != treeItem) {
						TreeViewEntity treeItemValue = treeItem.getValue();
						if (null != treeItemValue) {
							MouseButton button = event.getButton();
							if (MouseButton.SECONDARY.equals(button)) { // 右击
								String value = treeItemValue.getValue();
								String keyValue = treeItemValue.toString().trim().replace(" ", "");
								if (1 == event.getClickCount()) { // 右击一下TreeItem拷贝value
									ClipboardUtils.resetClipboardContent(value);
								} else if (2 == event.getClickCount()) {  // 右击两下TreeItem拷贝key-value
									ClipboardUtils.resetClipboardContent(keyValue);
								}
							} else if (MouseButton.PRIMARY.equals(button)) { //左击
								tableView.setItems(null); //清空tableView
								TreeViewEntity itemValue = treeItem.getValue();
								if (null != itemValue) {
									ObservableList<JsonTableEntity> tableEntities = itemValue.getTableEntities();
									if (null != tableEntities) {
										tableView.setItems(tableEntities);
									}
								}
							}
						}
					}
				}
			};
			treeView.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseClickEventHandler);
		}
	}

	private static void analyzeJsonObject(TreeItem<TreeViewEntity> root,
										  JSONObject rootObject,
										  ObservableList<JsonTableEntity> rootTableEntity) {
		Set<String> keySet = rootObject.keySet();
		List<String> keys = keySet.parallelStream().collect(Collectors.toList());
		Collections.sort(keys);
		keys.forEach(key -> {
			Object value = rootObject.get(key);
			if (value instanceof JSONObject) {
				JsonTableEntity currentNodeTable = new JsonTableEntity(key, TreeViewItemType.JSON_OBJECT.name(), TreeViewItemType.JSON_OBJECT.name());
				rootTableEntity.add(currentNodeTable);
				TreeViewEntity nodeEntity = new TreeViewEntity(key, TreeViewItemType.JSON_OBJECT.name(), TreeViewItemType.JSON_OBJECT);
				ObservableList<JsonTableEntity> nodeTableEntity = FXCollections.observableArrayList();
				nodeEntity.setTableEntities(nodeTableEntity);
				TreeItem<TreeViewEntity> node = new TreeItem<>(nodeEntity, ImageViewBuilder.createTreeViewNodeIcon());
				analyzeJsonObject(node, (JSONObject) value, nodeTableEntity);
				root.getChildren().add(node);
			} else if (value instanceof JSONArray) {
				analyzeJsonArray((JSONArray) value, root, key, rootTableEntity);
			} else if (value instanceof String) {
				root.getChildren().add(createStringLeaf(key, value, rootTableEntity));
			} else if (value instanceof Number) {
				root.getChildren().add(createNumberLeaf(key, value, rootTableEntity));
			} else if (value instanceof Boolean) {
				root.getChildren().add(createBooleanLeaf(key, value, rootTableEntity));
			} else if (null == value) {
				root.getChildren().add(createNullLeaf(key, rootTableEntity));
			}
		});
	}

	private static void analyzeJsonArray(JSONArray jsonArray,
										 TreeItem<TreeViewEntity> root,
										 String currentKey,
										 ObservableList<JsonTableEntity> rootTableEntity) {
		JsonTableEntity jsonTableEntity = new JsonTableEntity(currentKey, TreeViewItemType.JSON_ARRAY.name(), TreeViewItemType.JSON_ARRAY.name());
		rootTableEntity.add(jsonTableEntity);
		TreeViewEntity arrayNodeEntity = new TreeViewEntity(currentKey, TreeViewItemType.JSON_ARRAY.name(), TreeViewItemType.JSON_ARRAY);
		ObservableList<JsonTableEntity> arrayNodeTableEntity = FXCollections.observableArrayList();
		arrayNodeEntity.setTableEntities(arrayNodeTableEntity);
		TreeItem<TreeViewEntity> rootNode = new TreeItem<>(arrayNodeEntity, ImageViewBuilder.createTreeViewArrayNodeIcon());
		int index = 0;
		for (Object value : jsonArray) {
			String key = String.format("[%s]", index);
			if (value instanceof JSONObject) {
				JsonTableEntity currentNodeTable = new JsonTableEntity(currentKey, TreeViewItemType.JSON_OBJECT.name(), TreeViewItemType.JSON_OBJECT.name());
				arrayNodeTableEntity.add(currentNodeTable);
				TreeViewEntity nodeEntity = new TreeViewEntity(key, TreeViewItemType.JSON_OBJECT.name(), TreeViewItemType.JSON_OBJECT);
				ObservableList<JsonTableEntity> nodeTableEntity = FXCollections.observableArrayList();
				nodeEntity.setTableEntities(nodeTableEntity);
				TreeItem<TreeViewEntity> node = new TreeItem<>(nodeEntity, ImageViewBuilder.createTreeViewNodeIcon());
				analyzeJsonObject(node, (JSONObject) value, nodeTableEntity);
				rootNode.getChildren().add(node);
			} else if (value instanceof JSONArray) {
				analyzeJsonArray((JSONArray) value, rootNode, key, arrayNodeTableEntity);
			} else if (value instanceof String) {
				rootNode.getChildren().add(createStringLeaf(key, value, arrayNodeTableEntity));
			} else if (value instanceof Number) {
				rootNode.getChildren().add(createNumberLeaf(key, value, arrayNodeTableEntity));
			} else if (value instanceof Boolean) {
				rootNode.getChildren().add(createBooleanLeaf(key, value, arrayNodeTableEntity));
			} else if (null == value) {
				rootNode.getChildren().add(createNullLeaf(key, arrayNodeTableEntity));
			}
			index++;
		}
		root.getChildren().add(rootNode);
	}

	//创建字符串叶子节点
	private static TreeItem<TreeViewEntity> createStringLeaf(String key,
															 Object value,
															 ObservableList<JsonTableEntity> rootTableEntity) {
		String targetValue = (String) value;
		TreeViewEntity nodeEntity = new TreeViewEntity(key, targetValue, TreeViewItemType.STRING);
		JsonTableEntity jsonTableEntity = new JsonTableEntity(key, targetValue, TreeViewItemType.STRING.name());
		ObservableList<JsonTableEntity> nodeTableEntity = FXCollections.observableArrayList();
		nodeTableEntity.setAll(jsonTableEntity);
		nodeEntity.setTableEntities(nodeTableEntity);
		rootTableEntity.add(jsonTableEntity);
		return new TreeItem<>(nodeEntity, ImageViewBuilder.createTreeViewLeafIcon());
	}

	//创建Number类型叶子节点
	private static TreeItem<TreeViewEntity> createNumberLeaf(String key,
															 Object value,
															 ObservableList<JsonTableEntity> rootTableEntity) {
		String targetValue = String.valueOf(value);
		TreeViewEntity nodeEntity = new TreeViewEntity(key, targetValue, TreeViewItemType.NUMBER);
		JsonTableEntity jsonTableEntity = new JsonTableEntity(key, targetValue, TreeViewItemType.NUMBER.name());
		ObservableList<JsonTableEntity> nodeTableEntity = FXCollections.observableArrayList();
		nodeTableEntity.setAll(jsonTableEntity);
		nodeEntity.setTableEntities(nodeTableEntity);
		nodeEntity.setTableEntities(nodeTableEntity);
		rootTableEntity.add(jsonTableEntity);
		return new TreeItem<>(nodeEntity, ImageViewBuilder.createTreeViewLeafIcon());
	}

	//创建Boolean类型叶子节点
	private static TreeItem<TreeViewEntity> createBooleanLeaf(String key,
															  Object value,
															  ObservableList<JsonTableEntity> rootTableEntity) {
		String targetValue = String.valueOf(value);
		TreeViewEntity nodeEntity = new TreeViewEntity(key, targetValue, TreeViewItemType.BOOLEAN);
		JsonTableEntity jsonTableEntity = new JsonTableEntity(key, targetValue, TreeViewItemType.BOOLEAN.name());
		ObservableList<JsonTableEntity> nodeTableEntity = FXCollections.observableArrayList();
		nodeTableEntity.setAll(jsonTableEntity);
		nodeEntity.setTableEntities(nodeTableEntity);
		rootTableEntity.add(jsonTableEntity);
		return new TreeItem<>(nodeEntity, ImageViewBuilder.createTreeViewLeafIcon());
	}

	//创建Null叶子节点
	private static TreeItem<TreeViewEntity> createNullLeaf(String key,
														   ObservableList<JsonTableEntity> rootTableEntity) {
		String targetValue = NULL;
		TreeViewEntity nodeEntity = new TreeViewEntity(key, targetValue, TreeViewItemType.NULL);
		JsonTableEntity jsonTableEntity = new JsonTableEntity(key, targetValue, TreeViewItemType.NULL.name());
		ObservableList<JsonTableEntity> nodeTableEntity = FXCollections.observableArrayList();
		nodeTableEntity.setAll(jsonTableEntity);
		nodeEntity.setTableEntities(nodeTableEntity);
		rootTableEntity.add(jsonTableEntity);
		return new TreeItem<>(nodeEntity, ImageViewBuilder.createTreeViewLeafIcon());
	}
}
