package com.carl.myrpc.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * netty服务端
 */
public class MyRpcServer {
      /*开启Netty服务*/
      public void start(int port){
          //主线程,负责客户端的连接的建立,它不处理业务逻辑(线程池)
          EventLoopGroup bossGroup = new NioEventLoopGroup(1);
          //工作线程,默认的线程数为：cpu核数*2(线程池)
          EventLoopGroup workerGroup = new NioEventLoopGroup(2);
          try {
              //创建服务端对象->ServerBootStrap
              ServerBootstrap serverBootstrap = new ServerBootstrap();
              //给服务端对象绑定端口
              ChannelFuture future = serverBootstrap
                      //bossGroup处理当前链接对象
                      //workerGroup处理业务
                      .group(bossGroup,workerGroup)
                      //数据怎么来???什么协议????TCP
                      .channel(NioServerSocketChannel.class)//tcp协议通道
                      //怎么处理业务??handler,实际情况Handler()对象不止一个,是一个业务处理链
                      //.childHandler(new MyChannelHandler())//处理业务
                      .childHandler(new MyChannelInitializer())
                      .bind(port).sync();
              future.channel().closeFuture().sync();//当捕获异常或关闭当前应用
          } catch (Exception e){
               e.printStackTrace();
          }finally {
              //线程池优雅关闭
              bossGroup.shutdownGracefully();
              workerGroup.shutdownGracefully();
          }

      }
}
