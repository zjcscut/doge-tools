package org.throwable.doge.json;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.fxmisc.richtext.InlineCssTextArea;
import org.fxmisc.richtext.StyleClassedTextArea;
import org.throwable.doge.json.javafx.component.SceneStageEventHandler;
import org.throwable.doge.json.support.JavafxKeyCombinationFactory;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/9/29 21:15
 */
public class DogeJsonApplication extends Application {

    public void start(Stage primaryStage) throws Exception {
//		AlertHelper.createHelpAlert();
//		AlertHelper.createExceptionAlert(new RuntimeException("doge"),"doge","doge");
        InlineCssTextArea textArea = new InlineCssTextArea();
        textArea.setWrapText(true);
        textArea.setEditable(false);
        textArea.replaceText(0,0,"doge");
        textArea.setStyle(0,4,"-fx-fill: red");
        StackPane root = new StackPane();
        root.getChildren().add(textArea);
        Scene scene = new Scene(root, 300, 250);
        JavafxKeyCombinationFactory.register(JavafxKeyCombinationFactory.CONTROL_H, new SceneStageEventHandler(scene));
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
