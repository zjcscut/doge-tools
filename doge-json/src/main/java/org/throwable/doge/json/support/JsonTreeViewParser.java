package org.throwable.doge.json.support;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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

	public static void parseJsonObjectToTreeViewRootItem(TreeView<String> treeView, JSONObject jsonObject) {
		if (null != jsonObject) {
			TreeItem<String> root = new TreeItem<>(ROOT, ImageViewBuilder.createTreeViewRootIcon());
			analyzeJsonObject(root, jsonObject);
			treeView.setRoot(root);
			//添加鼠标左击的时候复制键,右击时候复制值
			EventHandler<MouseEvent> mouseClickEventHandler = event -> {
				if (MouseEvent.MOUSE_CLICKED.equals(event.getEventType())) {
					TreeItem<String> treeItem = treeView.getSelectionModel().getSelectedItem();
					if (null != treeItem) {
						String treeItemValue = treeItem.getValue();
						if (StringUtils.isNotBlank(treeItemValue)) {
							MouseButton button = event.getButton();
							if (MouseButton.SECONDARY.equals(button)) { // 右击
								String valueTrim = treeItemValue.trim().replace(" ", "");
								if (valueTrim.contains("[") && valueTrim.contains("]")) {
									valueTrim = valueTrim.substring(valueTrim.lastIndexOf("]") + 1);  //array node
								}
								String[] kv = valueTrim.split(":");
								String value;
								if (kv.length == 1) {
									value = "";
								} else {
									value = valueTrim.substring(valueTrim.indexOf(":") + 1);
								}
								if (1 == event.getClickCount()) { // 右击一下TreeItem拷贝value
									resetClipboardContent(value);
								} else if (2 == event.getClickCount()) {  // 右击两下TreeItem拷贝key-value
									resetClipboardContent(valueTrim);
								}
							}
						}
					}
				}
			};
			treeView.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseClickEventHandler);
		}
	}

	private static void analyzeJsonObject(TreeItem<String> root, JSONObject rootObject) {
		Set<String> keySet = rootObject.keySet();
		List<String> keys = keySet.parallelStream().collect(Collectors.toList());
		Collections.sort(keys);
		keys.parallelStream().forEach(key -> {
			Object value = rootObject.get(key);
			if (value instanceof JSONObject) {
				TreeItem<String> node = new TreeItem<>(key, ImageViewBuilder.createTreeViewNodeIcon());
				analyzeJsonObject(node, (JSONObject) value);
				root.getChildren().add(node);
			} else if (value instanceof JSONArray) {
				analyzeJsonArray((JSONArray) value, root, key);
			} else if (value instanceof String) {
				root.getChildren().add(createStringLeaf(key, value));
			} else if (value instanceof Number) {
				root.getChildren().add(createNonStringLeaf(key, value));
			} else if (value instanceof Boolean) {
				root.getChildren().add(createNonStringLeaf(key, value));
			} else if (null == value) {
				root.getChildren().add(createNullLeaf(key));
			}
		});
	}

	private static void analyzeJsonArray(JSONArray jsonArray, TreeItem<String> root, String currentKey) {
		TreeItem<String> rootNode = new TreeItem<>(currentKey, ImageViewBuilder.createTreeViewArrayNodeIcon());
		int index = 0;
		for (Object value : jsonArray) {
			String key = String.format("[%s]", index);
			if (value instanceof JSONObject) {
				TreeItem<String> node = new TreeItem<>(key, ImageViewBuilder.createTreeViewNodeIcon());
				analyzeJsonObject(node, (JSONObject) value);
				root.getChildren().add(node);
			} else if (value instanceof JSONArray) {
				analyzeJsonArray((JSONArray) value, root, key);
			} else if (value instanceof String) {
				root.getChildren().add(createStringLeaf(key, value));
			} else if (value instanceof Number) {
				root.getChildren().add(createNonStringLeaf(key, value));
			} else if (value instanceof Boolean) {
				root.getChildren().add(createNonStringLeaf(key, value));
			} else if (null == value) {
				root.getChildren().add(createNullLeaf(key));
			}
			index++;
		}
		root.getChildren().add(rootNode);
	}

	//字符串叶子节点
	private static TreeItem<String> createStringLeaf(String key, Object value) {
		return new TreeItem<>(String.format("%s : \"%s\"", key, String.valueOf(value)), ImageViewBuilder.createTreeViewLeafIcon());
	}

	//非字符串叶子节点
	private static TreeItem<String> createNonStringLeaf(String key, Object value) {
		return new TreeItem<>(String.format("%s : %s", key, String.valueOf(value)), ImageViewBuilder.createTreeViewLeafIcon());
	}

	//Null叶子节点
	private static TreeItem<String> createNullLeaf(String key) {
		return new TreeItem<>(String.format("%s : %s", key, NULL), ImageViewBuilder.createTreeViewLeafIcon());
	}

	private static void resetClipboardContent(String content) {
		Clipboard clipboard = Clipboard.getSystemClipboard();
		ClipboardContent clipboardContent = new ClipboardContent();
		clipboardContent.putString(content);
		clipboard.setContent(clipboardContent);
	}

}
