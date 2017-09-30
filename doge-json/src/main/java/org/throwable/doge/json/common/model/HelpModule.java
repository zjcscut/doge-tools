package org.throwable.doge.json.common.model;

import java.util.List;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/9/30 14:56
 */
public class HelpModule {

    private String description;
    private List<Help> helps;

    public HelpModule() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Help> getHelps() {
        return helps;
    }

    public void setHelps(List<Help> helps) {
        this.helps = helps;
    }
}
