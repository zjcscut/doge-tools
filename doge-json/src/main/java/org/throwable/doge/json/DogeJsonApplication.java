package org.throwable.doge.json;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.fxmisc.richtext.InlineCssTextArea;
import org.throwable.doge.json.javafx.handler.SceneStageEventHandler;
import org.throwable.doge.json.support.JavafxKeyCombinationFactory;
import org.throwable.doge.json.support.JsonFormatHelper;
import org.throwable.doge.json.support.JsonShaderEditor;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/9/29 21:15
 */
public class DogeJsonApplication extends Application {

	private static final Pattern NUMBER_PATTERN = Pattern.compile("[\\d]");
	private static final Pattern LEFT_BRACKET_PATTERN = Pattern.compile("\\{");
	private static final Pattern RIGHT_BRACKET_PATTERN = Pattern.compile("\\}");

	public void start(Stage primaryStage) throws Exception {
//		AlertViewFactory.createHelpAlert();
//		AlertViewFactory.createExceptionAlert(new RuntimeException("doge"),"doge","doge");
		InlineCssTextArea textArea = new InlineCssTextArea();
		textArea.setWrapText(true);
		textArea.setEditable(false);
		String json = "{\"key\":\"doge\",\"value\":10086}";
		String result = JsonFormatHelper.prettyToJsonFormat(json);
		textArea.replaceText(0, 0, result);
		textArea.setStyle("-fx-font-size: 18;-fx-font-family: consolas;-fx-border-style: solid;-fx-border-width: 2px");
		List<JsonShaderEditor.ShaderColorResult> shaderColorResults = JsonShaderEditor.shadeColorForJson(result);
		if (!shaderColorResults.isEmpty()) {
			for (JsonShaderEditor.ShaderColorResult shaderColorResult : shaderColorResults) {
				textArea.setStyle(shaderColorResult.getFrom(), shaderColorResult.getTo(), shaderColorResult.getFillColor());
			}
		}
		StackPane root = new StackPane();
		root.getChildren().add(textArea);
		Scene scene = new Scene(root, 300, 250);
//		scene.setOnKeyPressed(JavafxKeyCombinationFactory::process);
		JavafxKeyCombinationFactory.register(JavafxKeyCombinationFactory.CONTROL_H, new SceneStageEventHandler());
		primaryStage.setTitle("Hello World!");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
