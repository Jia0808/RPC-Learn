package com.carl.myrpc.usebytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public class read {
    public static void main(String[] args) {

        //构造
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello netty", CharsetUtil.UTF_8);

        System.out.println("byteBuf的容量为：" + byteBuf.capacity());
        System.out.println("byteBuf的可读容量为：" + byteBuf.readableBytes());
        System.out.println("byteBuf的可写容量为：" + byteBuf.writableBytes());

        while (byteBuf.isReadable()){ //方法一：内部通过移动readerIndex进行读取
            System.out.print((char)byteBuf.readByte());
        }
     /*
     byteBuf的容量为：64
     byteBuf的可读容量为：11
     byteBuf的可写容量为：53
     hello netty
      */

        //方法二：通过下标直接读取
//        for (int i = 0; i < byteBuf.readableBytes(); i++) {
//            System.out.println((char)byteBuf.getByte(i));
//        }
//
//        //方法三：转化为byte[]进行读取
//        byte[] bytes = byteBuf.array();
//        for (byte b : bytes) {
//            System.out.println((char)b);
//        }

    }
}
