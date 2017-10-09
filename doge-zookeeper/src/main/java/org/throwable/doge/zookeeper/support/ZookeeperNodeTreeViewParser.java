package org.throwable.doge.zookeeper.support;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
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

    public static void parseZookeeperNodeTreeView(TreeView<TreeViewNode> nodeTreeView,
                                                  CuratorFramework client) throws Exception {
        nodeTreeView.setRoot(parseNodeByPath(ROOT, client));
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
            childPaths.parallelStream().forEach(childPath -> {
                try {
                    item.getChildren().add(parseNodeByPath(String.format("/%s", childPath), client));
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
}
