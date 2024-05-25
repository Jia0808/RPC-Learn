package com.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class nettyServer {


    private final int port;

    public nettyServer(int port) {
        this.port = port;
    }

    /*netty 服务端启动*/
    public void runServer() throws Exception {
        //主线程,负责客户端的连接的建立,它不处理业务逻辑(线程池)
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //工作线程,默认的线程数为：cpu核数*2(线程池)
        EventLoopGroup workerGroup = new NioEventLoopGroup(2);
        try {
            // 启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) //协议
                    // tcp最大缓存链接 个数,它是tcp的参数, tcp_max_syn_backlog(半连接上限数量, CENTOS6.5默认是128)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .handler(new LoggingHandler(LogLevel.INFO)) //打印日志级别
                    .childHandler(new ChannelInitializer<SocketChannel>() { //channel任务链
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            //管道注册handler
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //编码通道处理
                            pipeline.addLast("decode", new StringDecoder());
                            pipeline.addLast("encode", new StringEncoder());
                            //处理接收到的请求 //可以配置多个
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });
            System.out.println("--------------服务端启动---------------");
            //监听输入端的消息并发送给所有客户端
            new Thread(() -> {
                try {
                    while (true) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                        String str = bufferedReader.readLine();
                        if (!NettyServerHandler.channelList.isEmpty()) {
                            for (Channel channel : NettyServerHandler.channelList) {
                                channel.writeAndFlush(str);
                            }
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

            //绑定端口开始链接
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            //优雅关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();

        }
    }

    public static void main(String[] args) throws Exception{
        new nettyServer(9999).runServer();
    }
}
