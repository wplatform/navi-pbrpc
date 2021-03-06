package com.baidu.beidou.navi.pbrpc.it;

import org.junit.Test;

import com.baidu.beidou.navi.pbrpc.client.PbrpcClient;
import com.baidu.beidou.navi.pbrpc.client.PbrpcClientFactory;
import com.baidu.beidou.navi.pbrpc.client.PooledConfiguration;
import com.baidu.beidou.navi.pbrpc.exception.client.OperationNotSupportException;
import com.baidu.beidou.navi.pbrpc.exception.client.PbrpcException;
import com.baidu.beidou.navi.pbrpc.transport.PbrpcMsg;

public class BlockingIOPooledPbrpcClientTest extends BaseTest {

    @Test
    public void testSyncCall() throws Exception {
        syncCall(new ClientBuilder() {
            @Override
            public PbrpcClient getClient() {
                return PbrpcClientFactory.buildPooledBlockingIOConnection(IP, PORT);
            }
        });
    }

    @Test
    public void testSyncCallTimeout() throws Exception {
        PbrpcMsg msg = getPbrpcMsg(1).setServiceId(101);
        syncCall(new ClientBuilder() {
            @Override
            public PbrpcClient getClient() {
                return PbrpcClientFactory.buildPooledBlockingIOConnection(IP, PORT, 2000);
            }
        }, msg, new PbrpcException(), true);
    }

    @Test
    public void testNegativeConnectCall() throws Exception {
        syncCall(new ClientBuilder() {
            @Override
            public PbrpcClient getClient() {
                return PbrpcClientFactory.buildPooledBlockingIOConnection("9.9.9.9", 9999);
            }
        }, new PbrpcException(), true);
    }

    @Test
    public void testNegativeASyncCall() throws Exception {
        asyncCall(new ClientBuilder() {
            @Override
            public PbrpcClient getClient() {
                return PbrpcClientFactory.buildPooledBlockingIOConnection(
                        new PooledConfiguration(), IP, PORT, 2000, 5000);
            }
        }, new OperationNotSupportException(), true);
    }

}
