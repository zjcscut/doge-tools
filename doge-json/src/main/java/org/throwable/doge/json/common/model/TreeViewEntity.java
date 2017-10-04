package org.throwable.doge.json.common.model;

import javafx.collections.ObservableList;
import org.throwable.doge.json.common.TreeViewItemType;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/10/3 下午12:39
 */
public class TreeViewEntity {

	private String key;
	private String value;
	private TreeViewItemType type;

	private ObservableList<JsonTableEntity> tableEntities;

	public TreeViewEntity(String key, String value, TreeViewItemType type) {
		this.key = key;
		this.value = value;
		this.type = type;
	}

	public ObservableList<JsonTableEntity> getTableEntities() {
		return tableEntities;
	}

	public void setTableEntities(ObservableList<JsonTableEntity> tableEntities) {
		this.tableEntities = tableEntities;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public TreeViewItemType getType() {
		return type;
	}

	public void setType(TreeViewItemType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return String.format("%s : %s", key, null == value ? "" : value);
	}
}
