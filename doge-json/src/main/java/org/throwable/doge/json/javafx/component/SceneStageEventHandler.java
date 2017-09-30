package org.throwable.doge.json.javafx.component;

import javafx.scene.Scene;
import org.throwable.doge.json.support.AlertHelper;
import org.throwable.doge.json.support.JavafxKeyCombinationFactory;
import org.throwable.doge.json.support.KeyCombinationPressedEventHandler;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/9/30 15:52
 */
public class SceneStageEventHandler implements KeyCombinationPressedEventHandler {

    private final Scene scene;

    public SceneStageEventHandler(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void handle() {
        AlertHelper.createHelpAlert();
    }

    @Override
    public void registerEvent() {
        scene.setOnKeyPressed(JavafxKeyCombinationFactory::process);
    }
}
