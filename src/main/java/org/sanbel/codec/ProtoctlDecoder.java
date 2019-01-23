package org.sanbel.codec;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.sanbel.common.Command;
import org.sanbel.heartBeat.HeartBeatMessage;

import java.util.List;

/**
 * Created by duanjigui on 2019/1/22.
 */
public class ProtoctlDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte[] magic=new byte[8];
        in.readBytes(magic,0,8);
        System.out.println("魔术："+new String(magic));
        Byte b= in.readByte();
        System.out.println("版本: "+b.toString() );
        byte[] c =new byte[32];
        in.readBytes(c);
        System.out.println("序列化形式: "+ in.readByte() );
        byte commond= in.readByte();
        System.out.println("指令: "+ commond );
        int length=in.readInt();
        System.out.println("长度: "+ length );
        byte[] body=new byte[length];
        in.readBytes(body);
        String value=  new String(body);
        System.out.println("数据: "+new String(body));
        switch (commond){
            case Command.LOGIN:
                // 转换为相关对象
                System.out.println("转换为Login对象");
                break;
            case Command.PING:
                System.out.println("转换为HeartBeatMessage对象");
                HeartBeatMessage message=  JSON.parseObject(body, HeartBeatMessage.class);
                out.add(message);
                break;
        }
        System.out.println("==============================================");

    }

}
