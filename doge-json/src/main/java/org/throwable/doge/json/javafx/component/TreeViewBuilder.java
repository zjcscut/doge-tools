package org.throwable.doge.json.javafx.component;

import javafx.scene.control.TreeView;
import org.throwable.doge.json.common.Constants;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/10/1 12:09
 */
public abstract class TreeViewBuilder {

	public static TreeView createTreeView() {
		TreeView treeView = new TreeView();
		treeView.setPrefWidth(Constants.DEFAULT_TREE_VIEW_WIDTH);
		treeView.setMinWidth(Constants.DEFAULT_PANE_MIN_WIDTH);
		return treeView;
	}
}
