package org.sanbel.codec;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.sanbel.common.Command;
import org.sanbel.heartBeat.HeartBeatMessage;

/**
 * Created by duanjigui on 2019/1/23.
 */
public class HeartBeatMessageEncode extends MessageToByteEncoder<HeartBeatMessage> {

    private String magicNumber= "AA50D351";  //8个字节

    private byte version=0x01; //版本 1个字节

    private byte searlizeType=0x01;  //序列化类型

    @Override
    protected void encode(ChannelHandlerContext ctx, HeartBeatMessage msg, ByteBuf out) throws Exception {

        System.err.println("心跳包解析！");

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
        out.writeByte(Command.PING);
        byte[] body=JSON.toJSONString(msg).getBytes();
        out.writeInt(body.length);
        out.writeBytes(body);
    }
}
