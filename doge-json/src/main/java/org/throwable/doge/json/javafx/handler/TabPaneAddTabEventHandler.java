package org.throwable.doge.json.javafx.handler;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.throwable.doge.json.support.KeyCombinationPressedEventHandler;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/10/1 11:40
 */
public class TabPaneAddTabEventHandler implements KeyCombinationPressedEventHandler {

	private static final AtomicLong COUNTER = new AtomicLong(1);
	private static final String TAB_NAME = "Tab-";
	private final TabPane tabPane;
	private final TabWrapper tabWrapper;

	public TabPaneAddTabEventHandler(TabPane tabPane, TabWrapper tabWrapper) {
		this.tabPane = tabPane;
		this.tabWrapper = tabWrapper;
	}

	@Override
	public void handle() {
		Tab tab = new Tab(TAB_NAME + COUNTER.getAndIncrement());
		tabWrapper.wrapTab(tab);
		tabPane.getTabs().add(tab);
		tabPane.getSelectionModel().select(tab); //必须重新选择当前tab
	}
}
