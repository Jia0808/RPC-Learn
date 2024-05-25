package com.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


/*handler*/
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    public static Channel serverChannel = null;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        serverChannel = ctx.channel();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("客户端收到消息： " + msg);
    }

    // 出现异常的处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.err.println("客户端读取数据出现异常");
        cause.printStackTrace();
        ctx.close();
    }


}
