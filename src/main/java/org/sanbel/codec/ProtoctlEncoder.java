package org.sanbel.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.sanbel.common.BasePacket;

/**
 * Created by duanjigui on 2019/1/22.
 */
public class ProtoctlEncoder extends MessageToByteEncoder<BasePacket> {

    private String magicNumber= "AA50D351";  //8个字节

    private byte version=0x01; //版本 1个字节

    private byte searlizeType=0x01;  //序列化类型

    @Override
    protected void encode(ChannelHandlerContext ctx, BasePacket msg, ByteBuf out) throws Exception {

        byte[] body= msg.simpleMsg();
        //魔数
        out.writeBytes(magicNumber.getBytes());
        //版本
        out.writeByte(version);
        // 64位空，凭证
        byte[] cert=new byte[32];
        out.writeBytes(cert);
        //序列化类型
        out.writeByte(searlizeType);
        // 指令  长度  数据
        out.writeBytes(body);
    }
}
