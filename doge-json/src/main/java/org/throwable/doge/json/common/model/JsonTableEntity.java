package org.throwable.doge.json.common.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/10/1 0:17
 */
public class JsonTableEntity {

	private final SimpleStringProperty key;
	private final SimpleStringProperty value;
	private final SimpleStringProperty type;

	public JsonTableEntity(String key, String value, String type) {
		this.key = new SimpleStringProperty(key);
		this.value = new SimpleStringProperty(value);
		this.type = new SimpleStringProperty(type);
	}

	public String getKey() {
		return key.get();
	}

	public SimpleStringProperty keyProperty() {
		return key;
	}

	public void setKey(String key) {
		this.key.set(key);
	}

	public String getValue() {
		return value.get();
	}

	public SimpleStringProperty valueProperty() {
		return value;
	}

	public void setValue(String value) {
		this.value.set(value);
	}

	public String getType() {
		return type.get();
	}

	public SimpleStringProperty typeProperty() {
		return type;
	}

	public void setType(String type) {
		this.type.set(type);
	}
}
