package org.throwable.doge.json.javafx.component;

import javafx.scene.control.TableView;
import org.throwable.doge.json.common.Constants;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/10/1 12:11
 */
public abstract class TableViewBuilder {

	public static TableView createTableView(){
		TableView tableView = new TableView();
		tableView.setPrefWidth(Constants.DEFAULT_TABLE_VIEW_WIDTH);
        tableView.setMinWidth(Constants.DEFAULT_PANE_MIN_WIDTH);
		registerTableViewEventAction(tableView);
		return tableView;
	}

	private static void registerTableViewEventAction(TableView tableView){

	}
}
