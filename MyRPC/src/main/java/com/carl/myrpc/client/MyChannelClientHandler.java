package com.carl.myrpc.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;


public class MyChannelClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    /*获取响应数据*/
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        System.out.println("来自服务端的数据:"+byteBuf.toString(CharsetUtil.UTF_8));
    }

    /*向服务端发送数据*/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("你好,服务端".getBytes(CharsetUtil.UTF_8)));
    }

    /*异常处理*/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
