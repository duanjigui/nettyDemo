package org.sanbel.common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

/**
 * 基本packet
 * 其实对应的每一个请求，只要暴露 body和header方法即可
 *
 * Created by duanjigui on 2019/1/21.
 */
public abstract class BasePacket implements Packet{

    private String magicNumber= "AA50D351";  //8个字节

    private byte version=0x01; //版本 1个字节

    private byte searlizeType=0x01;  //序列化类型

    //这里会构造一个抽象方法
    public abstract byte[] simpleMsg();

}
