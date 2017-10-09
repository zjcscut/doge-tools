package org.throwable.doge.zookeeper;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.throwable.doge.zookeeper.common.model.TreeViewNode;
import org.throwable.doge.zookeeper.support.ZookeeperNodeTreeViewParser;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/10/1 0:28
 */
@SuppressWarnings("unchecked")
public class TreeViewMain extends Application {


	@Override
	public void start(Stage primaryStage) throws Exception {
		TestingServer testingServer = new TestingServer();
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework curatorFramework =
				CuratorFrameworkFactory.builder()
						.connectString("localhost:2181")
						.sessionTimeoutMs(5000)
						.connectionTimeoutMs(5000)
						.retryPolicy(retryPolicy)
						.build();
		curatorFramework.start();
		TreeView<TreeViewNode> nodeTreeView = new TreeView<>();
		ZookeeperNodeTreeViewParser.parseZookeeperNodeTreeView(nodeTreeView,curatorFramework);
		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(nodeTreeView);
		Scene scene = new Scene(new Group());
		primaryStage.setTitle("Table View Sample");
		primaryStage.setWidth(450);
		primaryStage.setHeight(500);
		((Group) scene.getRoot()).getChildren().addAll(vbox);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
