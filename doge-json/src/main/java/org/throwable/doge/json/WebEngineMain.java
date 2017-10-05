package org.throwable.doge.json;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/10/5 下午1:07
 */
public class WebEngineMain extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		stage.setWidth(400);
		stage.setHeight(500);
		Scene scene = new Scene(new Group());
		final WebView browser = new WebView();
		browser.setPrefHeight(500);
		browser.setPrefWidth(400);
		browser.setContextMenuEnabled(false);
		createContextMenu(browser);
		final WebEngine webEngine = browser.getEngine();
		//用这种方式可以加载本地的html
		String url = getClass().getResource("/static/views/form.html").toExternalForm();
		webEngine.load(url);
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(browser);
		scene.setRoot(scrollPane);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	private static void createContextMenu(WebView webView) {
		ContextMenu contextMenu = new ContextMenu();
		MenuItem reload = new MenuItem("Reload");
		reload.setOnAction(e -> webView.getEngine().reload());
		MenuItem savePage = new MenuItem("Save Page");
		savePage.setOnAction(e -> System.out.println("Save page..."));
		MenuItem hideImages = new MenuItem("Hide Images");
		hideImages.setOnAction(e -> System.out.println("Hide Images..."));
		contextMenu.getItems().addAll(reload, savePage, hideImages);
		webView.setOnMousePressed(e -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				contextMenu.show(webView, e.getScreenX(), e.getScreenY());
			} else {
				contextMenu.hide();
			}
		});
	}
}
