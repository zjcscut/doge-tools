package org.throwable.doge;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.throwable.doge.json.common.Constants;
import org.throwable.doge.json.javafx.ViewController;

/**
 * @author throwable
 * @version v1.0
 * @description 启动主函数
 * @since 2017/9/15 0:19
 */
public class MainController extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		ViewController viewController = new ViewController();
		Scene scene = new Scene(viewController.initViewsAndReturnRoot(), -1, -1);
		stage.setTitle(Constants.DEFAULT_PRODUCT_NAME);
		stage.getIcons().add(new Image(MainController.class.getResourceAsStream(Constants.DEFAULT_LOGO_PATH)));
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
