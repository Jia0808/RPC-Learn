package com.carl.myrpc.usebytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class write {
    public static void main(String[] args) {

        //构造空的字节缓冲区，初始大小为10，最大为20
        ByteBuf byteBuf = Unpooled.buffer(10,20);

        System.out.println("byteBuf的容量为：" + byteBuf.capacity());
        System.out.println("byteBuf的可读容量为：" + byteBuf.readableBytes());
        System.out.println("byteBuf的可写容量为：" + byteBuf.writableBytes());
        /*
         * byteBuf的容量为：10
         * byteBuf的可读容量为：0
         * byteBuf的可写容量为：10
         */

        for (int i = 0; i < 5; i++) {
            byteBuf.writeInt(i); //写入int类型，一个int占4个字节
        }
        System.out.println("ok");

        System.out.println("byteBuf的容量为：" + byteBuf.capacity());
        System.out.println("byteBuf的可读容量为：" + byteBuf.readableBytes());
        System.out.println("byteBuf的可写容量为：" + byteBuf.writableBytes());
        /*
         * byteBuf的容量为：20
         * byteBuf的可读容量为：20
         * byteBuf的可写容量为：0
         */
        while (byteBuf.isReadable()){
            int i = byteBuf.readInt();
            System.out.print(i + ",");
        }

    }
}
