package com.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.ArrayList;
import java.util.List;

/*handler*/
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    public static List<Channel> channelList = new ArrayList<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        channelList.add(ctx.channel());
    }

    //读取数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("服务端收到消息: " + msg);
        ctx.writeAndFlush(Unpooled.copiedBuffer("success!!!", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("服务端出现异常:");
        cause.printStackTrace();
        ctx.close();
    }
}
