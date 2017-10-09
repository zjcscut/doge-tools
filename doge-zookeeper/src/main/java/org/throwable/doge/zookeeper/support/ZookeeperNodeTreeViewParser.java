package org.throwable.doge.zookeeper.support;

import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.throwable.doge.zookeeper.common.model.TreeViewContent;
import org.throwable.doge.zookeeper.common.model.TreeViewNode;
import org.throwable.doge.zookeeper.common.model.ZookeeperAclPair;
import org.throwable.doge.zookeeper.exception.ZookeeperNodeParseException;
import org.throwable.doge.zookeeper.javafx.component.ImageViewBuilder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/10/9 17:02
 */
public abstract class ZookeeperNodeTreeViewParser {

	private static final String ROOT = "/";
	private static final String PREFIX = "/";

	public static void parseZookeeperNodeTreeView(TreeView<TreeViewNode> nodeTreeView,
												  CuratorFramework client) throws Exception {
		nodeTreeView.setRoot(parseNodeByPath(ROOT, client));
		EventHandler<MouseEvent> mouseClickEventHandler = event -> {  //添加鼠标点击事件处理器

		};
		nodeTreeView.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseClickEventHandler);
//		nodeTreeView.setContextMenu();  //添加菜单
	}

	private static TreeItem<TreeViewNode> createFoldTreeItem(TreeViewNode node) {
		return new TreeItem<>(node, ImageViewBuilder.createDefaultFoldCloseImageView());
	}

	private static TreeItem<TreeViewNode> createFileTreeItem(TreeViewNode node) {
		return new TreeItem<>(node, ImageViewBuilder.createDefaultFileImageView());
	}

	private static TreeItem<TreeViewNode> parseNodeByPath(String currentPath,
														  CuratorFramework client) throws Exception {
		TreeItem<TreeViewNode> item;
		TreeViewNode currentNode = new TreeViewNode();
		currentNode.setCurrentPath(currentPath);
		currentNode.setChildPaths(parseChildPaths(currentPath, client));
		currentNode.setContent(parseNodeContent(currentPath, client));
		List<String> childPaths = currentNode.getChildPaths();
		if (null != childPaths && !childPaths.isEmpty()) {  //存在子节点
			item = createFoldTreeItem(currentNode);
			childPaths.parallelStream()
					.forEach(childPath -> {
						try {
							item.getChildren().add(parseNodeByPath(clearPath(String.format("%s%s",
									formatPath(currentPath),
									formatPath(childPath))),
									client));
						} catch (Exception e) {
							throw new ZookeeperNodeParseException(e);
						}
					});
		} else {  //不存在子节点
			item = createFileTreeItem(currentNode);
		}
		return item;
	}

	private static List<String> parseChildPaths(String currentPath,
												CuratorFramework client) throws Exception {
		return client.getChildren().forPath(currentPath);
	}

	private static TreeViewContent parseNodeContent(String currentPath,
													CuratorFramework client) throws Exception {
		TreeViewContent content = new TreeViewContent();
		Stat stat = new Stat();
		byte[] data = client.getData().storingStatIn(stat).forPath(currentPath);
		List<ACL> acls = client.getACL().forPath(currentPath);
		content.setStat(stat);
		content.setData(new String(data));
		if (null != acls && !acls.isEmpty()) {
			content.setAcls(acls.parallelStream().map(ZookeeperAclPair::new).collect(Collectors.toList()));
		}
		return content;
	}

	private static String formatPath(String path) {
		if (path.startsWith(PREFIX)) {
			return path;
		}
		return String.format("/%s", path);
	}

	private static String clearPath(String path) {
		return path.replace("//", "/");
	}

	private static void deleteNodeForPath(String path,
										  CuratorFramework client) throws Exception {
		client.delete().deletingChildrenIfNeeded().forPath(path);
	}
}
