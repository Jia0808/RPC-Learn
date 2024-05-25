package com.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class nettyClient {
    private final String ip;
    // 服务器的端口
    private final int port;

    public nettyClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    // 启动服务
    private void runServer() throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        Bootstrap bs = new Bootstrap();
        bs.group(bossGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        //管道注册handler
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        //编码通道处理
                        pipeline.addLast("decode", new StringDecoder());
                        //转码通道处理
                        pipeline.addLast("encode", new StringEncoder());
                        // 处理来自服务端的响应信息
                        socketChannel.pipeline().addLast(new
                                NettyClientHandler());
                    }
                });
        System.out.println("-------客户端 启动------");
        // 客户端开启
        ChannelFuture cf = bs.connect(ip, port).sync();
        String reqStr = "客户端发起连接请求";
        Channel channel = cf.channel();

        // 发送客户端的请求
        channel.writeAndFlush(reqStr);

        new Thread(() -> {
            try {
                while (true) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                    String msg = in.readLine();
                    channel.writeAndFlush(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();


    }

    public static void main(String[] args) throws Exception {
        new nettyClient("127.0.0.1", 9999).runServer();
    }

}

