package org.throwable.doge.json.support;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.throwable.doge.json.common.Constants;
import org.throwable.doge.json.common.model.Configuration;
import org.throwable.doge.json.common.model.Help;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.util.List;

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
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(600, 600);
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
        ImageView image = new ImageView();
        image.setFitHeight(150);
        image.setFitWidth(150);
        image.setImage(new Image(AlertHelper.class.getResourceAsStream(Constants.DEFAULT_IMG_PATH + "surprise.jpg")));
        expContent.add(image, 0, 0);
        expContent.add(label, 0, 1);
        expContent.add(textArea, 0, 2);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(AlertHelper.class.getResourceAsStream(Constants.DEFAULT_LOGO_PATH)));
        alert.getDialogPane().setExpandableContent(expContent);
        alert.getDialogPane().setExpanded(true);
        alert.showAndWait();
    }

    public static void createHelpAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("");  //去掉消息header
        alert.setTitle("Help");
        alert.setGraphic(null);  //去掉提示图标
        alert.getDialogPane().setPrefSize(180, 400);
        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(1);
        grid.setPadding(new Insets(25, 25, 25, 15));
        ImageView image = new ImageView();
        image.setFitHeight(150);
        image.setFitWidth(150);
        image.setImage(new Image(AlertHelper.class.getResourceAsStream(Constants.DEFAULT_LOGO_PATH)));
        grid.add(image, 0, 0);
        Configuration configuration = ConfigurationHelper.getConfiguration();
        Text author = new Text(String.format("Author：%s", configuration.getBaseInfo().getAuthor()));
        grid.add(author, 0, 1);
        Text contact = new Text(String.format("Contact：%s", configuration.getBaseInfo().getContact()));
        grid.add(contact, 0, 2);
        Hyperlink projectLink = new Hyperlink("Project Github");
        projectLink.setOnAction(event -> {
            try {
                URI uri = new URI(configuration.getBaseInfo().getProjectUrl());
                Constants.DESKTOP.browse(uri);
            } catch (Exception e) {
                //ignore
            }
        });
        grid.add(projectLink, 0, 4);
        Text version = new Text(String.format("Version：%s", configuration.getBaseInfo().getVersion()));
        grid.add(version, 0, 3);
        Label label = new Label("Help：");
        label.setFont(new Font(18));
        grid.add(label, 0, 5);
        int rowIndex = 6;
        List<Help> helps = configuration.getHelpModule().getHelps();
        for (Help help : helps) {
            Text text = new Text(String.format("%s：%s", help.getKey(), help.getDescription()));
            grid.add(text, 0, rowIndex);
            rowIndex++;
        }
        alert.getDialogPane().setContent(grid);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(AlertHelper.class.getResourceAsStream(Constants.DEFAULT_LOGO_PATH)));
        alert.showAndWait();
    }
}
