package org.throwable.doge.json.common.model;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/9/30 14:54
 */
public class Configuration {

    private BaseInfo baseInfo;
    private HelpModule helpModule;

    public Configuration() {
    }

    public BaseInfo getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(BaseInfo baseInfo) {
        this.baseInfo = baseInfo;
    }

    public HelpModule getHelpModule() {
        return helpModule;
    }

    public void setHelpModule(HelpModule helpModule) {
        this.helpModule = helpModule;
    }
}
