package com.carl.myrpc.usebytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * 丢弃
 * +-------------------+------------------+------------------+
 * | discardable bytes | readable bytes   | writable bytes   |
 * +-------------------+------------------+------------------+
 * 0          <=  readerIndex  <=  writerIndex     <=    capacity
 *
 */
public class discardRead {
    public static void main(String[] args) {

        ByteBuf byteBuf = Unpooled.copiedBuffer("hello netty", CharsetUtil.UTF_8);

        System.out.println("byteBuf的容量为：" + byteBuf.capacity());
        System.out.println("byteBuf的可读容量为：" + byteBuf.readableBytes());
        System.out.println("byteBuf的可写容量为：" + byteBuf.writableBytes());

        while (byteBuf.isReadable()){
            System.out.print((char)byteBuf.readByte());
        }
        System.out.println(" ");

        byteBuf.discardReadBytes(); //丢弃已读的字节空间

        System.out.println("byteBuf的容量为：" + byteBuf.capacity());
        System.out.println("byteBuf的可读容量为：" + byteBuf.readableBytes());
        System.out.println("byteBuf的可写容量为：" + byteBuf.writableBytes());
        /*
         * byteBuf的容量为：64
         * byteBuf的可读容量为：11
         * byteBuf的可写容量为：53
         * hello netty
         * byteBuf的容量为：64
         * byteBuf的可读容量为：0
         * byteBuf的可写容量为：64
         */

    }

}
