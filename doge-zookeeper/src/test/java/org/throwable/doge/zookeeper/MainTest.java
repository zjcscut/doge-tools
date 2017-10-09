package org.throwable.doge.zookeeper;

import javafx.scene.control.TreeView;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;
import org.throwable.doge.zookeeper.common.model.TreeViewNode;
import org.throwable.doge.zookeeper.support.ZookeeperNodeTreeViewParser;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2017/10/9 10:04
 */
public class MainTest {

    private CuratorFramework curatorFramework;

    @Before
    public void setUp() throws Exception {
        TestingServer testingServer = new TestingServer();
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        this.curatorFramework =
                CuratorFrameworkFactory.builder()
                        .connectString("localhost:2181")
                        .sessionTimeoutMs(5000)
                        .connectionTimeoutMs(5000)
                        .retryPolicy(retryPolicy)
                        .build();
        this.curatorFramework.start();
    }

    @Test
    public void testCreate() throws Exception {
        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/path", "123".getBytes());
        Thread.sleep(10);
        Stat stat = new Stat();
        byte[] data = curatorFramework.getData().storingStatIn(stat).forPath("/path");
        List<ACL> acls = curatorFramework.getACL().forPath("/path");
        System.out.println("data for path : /path --> " + new String(data));
        System.out.println("stat for path : /path --> " + stat);
        System.out.println("acls for path : /path --> " + acls);

        curatorFramework.setACL().withACL(ZooDefs.Ids.READ_ACL_UNSAFE).forPath("/path");

        List<String> childPaths = curatorFramework.getChildren().forPath("/");
		System.out.println(childPaths);
        childPaths = curatorFramework.getChildren().forPath("/zookeeper");
        System.out.println(childPaths);
		childPaths = curatorFramework.getChildren().forPath("/zookeeper/quota");
		System.out.println(childPaths);
        byte[] d = curatorFramework.getData().forPath("/zookeeper/quota");
 		System.out.println(new String(d));

        System.out.println("success");
    }

}