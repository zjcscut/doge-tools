package org.throwable.doge.json.javafx.handler;

import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.InlineCssTextArea;
import org.throwable.doge.json.common.Constants;
import org.throwable.doge.json.common.model.JsonTableEntity;
import org.throwable.doge.json.javafx.component.InlineCssTextAreaBuilder;
import org.throwable.doge.json.javafx.component.TableViewBuilder;
import org.throwable.doge.json.javafx.component.TreeViewBuilder;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/10/1 11:52
 */
public class DefaultTabWrapper implements TabWrapper {

	@Override
	public void wrapTab(Tab tab) {
		HBox hBox = new HBox();
		//create tree view
		TreeView treeView = TreeViewBuilder.createTreeView();
		HBox.setHgrow(treeView, Priority.ALWAYS);
		hBox.getChildren().add(treeView);
		//create text area
		InlineCssTextArea inlineCssTextArea = InlineCssTextAreaBuilder.createInlineCssTextArea();
		VirtualizedScrollPane<InlineCssTextArea> virtualizedScrollPane = new VirtualizedScrollPane<>(inlineCssTextArea);
		virtualizedScrollPane.setPrefWidth(Constants.DEFAULT_TEXT_AREA_WIDTH);
		virtualizedScrollPane.setMinWidth(Constants.DEFAULT_PANE_MIN_WIDTH);
		HBox.setHgrow(virtualizedScrollPane, Priority.ALWAYS);
		hBox.getChildren().add(virtualizedScrollPane);
		//create table view
		TableView<JsonTableEntity> tableView = TableViewBuilder.createTableView();
		HBox.setHgrow(tableView, Priority.ALWAYS);
		hBox.getChildren().add(tableView);
		tab.setContent(hBox);
	}
}
