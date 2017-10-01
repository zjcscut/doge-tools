package org.throwable.doge.json.support;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.throwable.doge.json.common.Constants;
import org.throwable.doge.json.exception.FileChooserException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/9/30 0:00
 */
public abstract class FileChooserHelper {

	public static void processSaveFileByTextContent(String value) {
		Stage stage = new Stage();
		stage.setMaxHeight(Constants.DEFAULT_ALERT_HEIGHT);
		stage.setMaxWidth(Constants.DEFAULT_ALERT_WIDTH);
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save operation");
		fileChooser.setInitialDirectory(new File(Constants.DEFAULT_OUTPUT_PATH));
		File file = fileChooser.showSaveDialog(stage);
		if (null != file) {
			try (PrintStream printStream = new PrintStream(new FileOutputStream(file))) {
				printStream.print(value);
				printStream.flush();
			} catch (Exception e) {
				Alert error = AlertViewFactory.createAlert(Alert.AlertType.ERROR,
						"保存文件", "保存文件", "保存文件异常!");
				error.showAndWait();
				throw new FileChooserException(e);
			}
		}
	}

}
