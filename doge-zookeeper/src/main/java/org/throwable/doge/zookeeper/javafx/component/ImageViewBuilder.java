package org.throwable.doge.zookeeper.javafx.component;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.throwable.doge.zookeeper.common.Constants;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/10/9 17:20
 */
public abstract class ImageViewBuilder {

    private static ImageView createImageView(String url, double height, double width) {
        ImageView imageView = new ImageView(new Image(ImageViewBuilder.class.getResourceAsStream(url)));
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        return imageView;
    }

    public static ImageView createDefaultFoldCloseImageView() {
        return createImageView(Constants.DEFAULT_IMG_FOLD_PATH,
                Constants.DEFAULT_TREE_VIEW_ICON_H,
                Constants.DEFAULT_TREE_VIEW_ICON_W);
    }

    public static ImageView createDefaultFileImageView() {
        return createImageView(Constants.DEFAULT_IMG_FILE_PATH,
                Constants.DEFAULT_TREE_VIEW_ICON_H,
                Constants.DEFAULT_TREE_VIEW_ICON_W);
    }
}
