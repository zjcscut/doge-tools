package org.throwable.doge.zookeeper.common.model;

import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/10/9 10:22
 */
public class TreeViewContent {

    private Stat stat;
    private String data;
    private List<ZookeeperAclPair> acls;

    public Stat getStat() {
        return stat;
    }

    public void setStat(Stat stat) {
        this.stat = stat;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<ZookeeperAclPair> getAcls() {
        return acls;
    }

    public void setAcls(List<ZookeeperAclPair> acls) {
        this.acls = acls;
    }
}
