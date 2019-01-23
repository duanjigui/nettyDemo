package org.sanbel.common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteBuffer;

/**
 * Created by duanjigui on 2019/1/22.
 */
public class LoginPacket extends BasePacket {


    @Override
    public byte[] simpleMsg() {
        ByteBuf buf= Unpooled.buffer();
        buf.writeByte(Command.LOGIN);
        String body="name=admin&pwd=12345678";
        buf.writeInt(body.getBytes().length);
        buf.writeBytes(body.getBytes());
        byte[] bytes=new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        return bytes;
    }
}
