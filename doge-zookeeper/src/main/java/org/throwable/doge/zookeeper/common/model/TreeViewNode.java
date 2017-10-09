package org.throwable.doge.zookeeper.common.model;

import java.util.List;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/10/9 10:21
 */
public class TreeViewNode {

    private String currentPath;
    private List<String> childPaths;
    private TreeViewContent content;

    public String getCurrentPath() {
        return currentPath;
    }

    public void setCurrentPath(String currentPath) {
        this.currentPath = currentPath;
    }

    public List<String> getChildPaths() {
        return childPaths;
    }

    public void setChildPaths(List<String> childPaths) {
        this.childPaths = childPaths;
    }

    public TreeViewContent getContent() {
        return content;
    }

    public void setContent(TreeViewContent content) {
        this.content = content;
    }

	@Override
	public String toString() {
		return currentPath;
	}
}
