package com.carl.myrpc.server;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;


/**
 * 业务操作
 */
public class MyChannelHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取客户端传入的数据
     * @param ctx 上下文对象 就是记录客户端请求过来相关的信息
     * @param msg 客户端传过来的数据
     * @throws Exception 异常
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg; //netty之间进行通讯
        String content = byteBuf.toString(StandardCharsets.UTF_8);
        System.out.println("客户端传过来的数据: " + content);
        //同时还需要将数据响应给客户端
        //不管是传入数据还是传出数据其实都是用ByteBuf
        ctx.writeAndFlush(Unpooled.copiedBuffer("success!!!", CharsetUtil.UTF_8));
    }

    /**
     * 如果有异常直接关闭上下文对象
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
