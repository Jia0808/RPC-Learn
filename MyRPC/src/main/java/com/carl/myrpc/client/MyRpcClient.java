package com.carl.myrpc.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyRpcClient {

    /*启动客户端*/
    public void start(String host,int port){
        //工作线程,默认的线程数为：cpu核数*2(线程池)
        EventLoopGroup workerGroup = new NioEventLoopGroup(2);
        try {
            //创建启动客户端对象
            ChannelFuture channelFuture = new Bootstrap()
                    .group(workerGroup)
                    .channel(NioSocketChannel.class)//tcp协议通道
                    .handler(new MyChannelClientHandler()) //业务处理
                    .connect(host, port).sync();//绑定ip,port建立连接
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //优雅关闭
            workerGroup.shutdownGracefully();
        }

    }

}
