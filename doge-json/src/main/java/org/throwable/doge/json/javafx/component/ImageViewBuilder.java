package org.throwable.doge.json.javafx.component;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.throwable.doge.json.common.Constants;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/10/1 12:19
 */
public abstract class ImageViewBuilder {

	private static ImageView createImageView(String url) {
		ImageView imageView = new ImageView(new Image(ImageViewBuilder.class.getResourceAsStream(url)));
		imageView.setFitHeight(Constants.DEFAULT_TREE_VIEW_ICON_R);
		imageView.setFitWidth(Constants.DEFAULT_TREE_VIEW_ICON_R);
		return imageView;
	}

	public static ImageView createTreeViewRootIcon() {
		return createImageView(Constants.DEFAULT_IMG_ROOT_PATH);
	}

	public static ImageView createTreeViewNodeIcon() {
		return createImageView(Constants.DEFAULT_IMG_NODE_PATH);
	}

	public static ImageView createTreeViewLeafIcon() {
		return createImageView(Constants.DEFAULT_IMG_LEAF_PATH);
	}

	public static ImageView createTreeViewArrayNodeIcon() {
		return createImageView(Constants.DEFAULT_IMG_ARRAY_NODE_PATH);
	}
}
