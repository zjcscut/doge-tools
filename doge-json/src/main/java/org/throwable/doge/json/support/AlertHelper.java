package org.throwable.doge.json.support;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.throwable.doge.json.common.Constants;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/9/29 23:53
 */
public abstract class AlertHelper {

	public static Alert createAlert(Alert.AlertType alertType, String title, String header, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(AlertHelper.class.getResourceAsStream(Constants.DEFAULT_LOGO_PATH)));
		return alert;
	}

	public static void createExceptionAlert(Throwable ex, String header, String content) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("吃惊！抛异常了");
		alert.setHeaderText(header);
		alert.setContentText(content);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String exceptionText = sw.toString();
		pw.close();
		try {
			sw.close();
		} catch (IOException e) {
			//ignore
		}
		Label label = new Label("下面是详细的异常栈：");
		label.setFont(new Font(18));
		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);
		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);
		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		ImageView image1 = new ImageView();
		image1.setFitHeight(150);
		image1.setFitWidth(150);
		image1.setImage(new Image(AlertHelper.class.getResourceAsStream(Constants.DEFAULT_IMG_PATH + "surprise.jpg")));
		expContent.add(image1, 0, 0);
		expContent.add(label, 0, 1);
		expContent.add(textArea, 0, 2);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(AlertHelper.class.getResourceAsStream(Constants.DEFAULT_LOGO_PATH)));
		alert.getDialogPane().setExpandableContent(expContent);
		alert.getDialogPane().setExpanded(true);
		alert.showAndWait();
	}
}
