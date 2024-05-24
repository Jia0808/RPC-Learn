package com.carl.myrpc;

import com.carl.myrpc.client.MyRpcClient;
import com.carl.myrpc.server.MyRpcServer;
import org.junit.Test;

public class RpcTest {

     /*服务端启动*/
    @Test
    public void testMyRPCServerStart(){
        new MyRpcServer().start(5555);//开一个端口
    }

    /*客户端启动*/
    @Test
    public void  testMyRPCClientStart(){
        new MyRpcClient().start("127.0.0.1",5555);
    }

}
