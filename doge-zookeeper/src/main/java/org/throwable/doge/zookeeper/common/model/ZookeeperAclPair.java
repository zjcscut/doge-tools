package org.throwable.doge.zookeeper.common.model;

import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/10/9 10:28
 */
public class ZookeeperAclPair {

    private Integer perms;
    private String scheme;
    private String id;

    public ZookeeperAclPair(ACL acl) {
        if (null != acl) {
            this.perms = acl.getPerms();
            Id id = acl.getId();
            if (null != id) {
                this.scheme = id.getScheme();
                this.id = id.getId();
            }
        }
    }

    public Integer getPerms() {
        return perms;
    }

    public String getScheme() {
        return scheme;
    }

    public String getId() {
        return id;
    }
}
