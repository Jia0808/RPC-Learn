package com.carl.myrpc.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    /*多个业务链添加*/
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new MyChannelHandler());
    }
}
