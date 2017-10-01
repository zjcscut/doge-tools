package org.throwable.doge.json.javafx.handler;

import org.throwable.doge.json.support.AlertViewFactory;
import org.throwable.doge.json.support.KeyCombinationPressedEventHandler;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/9/30 15:52
 */
public class SceneStageEventHandler implements KeyCombinationPressedEventHandler {

    @Override
    public void handle() {
        AlertViewFactory.createHelpAlert();
    }

}
