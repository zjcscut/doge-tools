package org.throwable.doge.json.support;

import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.throwable.doge.json.common.Constants;
import org.throwable.doge.json.exception.DirectoryChooserException;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/9/29 23:51
 */
public abstract class DirectoryChooserHelper {

	public static String chooseOutputPath() {
		String defaultOutputPath = Constants.DEFAULT_OUTPUT_PATH;
		Stage stage = new Stage();
		stage.setMaxHeight(Constants.DEFAULT_ALERT_HEIGHT);
		stage.setMaxWidth(Constants.DEFAULT_ALERT_WIDTH);
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Choose save path");
		directoryChooser.setInitialDirectory(new File(defaultOutputPath));
		File file = directoryChooser.showDialog(stage);
		if (null != file) {
			EventQueue.invokeLater(() -> {
				try {
					Constants.DESKTOP.open(file);
				} catch (IOException e) {
					Alert error = AlertViewFactory.createAlert(Alert.AlertType.ERROR,
							"打开文件搜索器异常", "打开文件搜索器异常", "打开文件搜索器异常");
					error.showAndWait();
					throw new DirectoryChooserException(e);
				}
			});
		}
		if (null != file) {
			defaultOutputPath = file.getAbsolutePath();
		}
		return defaultOutputPath;
	}
}
