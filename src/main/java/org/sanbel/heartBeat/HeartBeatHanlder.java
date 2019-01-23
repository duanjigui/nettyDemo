package org.sanbel.heartBeat;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 用户心跳消息处理器
 * Created by duanjigui on 2019/1/23.
 */
public class HeartBeatHanlder extends ChannelDuplexHandler {

    //重写心跳包
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)){
                HeartBeatMessage message =new HeartBeatMessage(HeartBeatMessage.PING);
                ctx.writeAndFlush(message); //发送心跳消息
                System.out.println("read idle!");
            }
            if (event.state().equals(IdleState.WRITER_IDLE)){
                HeartBeatMessage message =new HeartBeatMessage(HeartBeatMessage.PING);
                ctx.writeAndFlush(message); //发送心跳消息
                System.out.println("write idle!");
            }
            if (event.state().equals(IdleState.ALL_IDLE)){
                System.out.println("all idle!");
                HeartBeatMessage message =new HeartBeatMessage(HeartBeatMessage.PING);
                ctx.writeAndFlush(message); //发送心跳消息
            }
        }
        super.userEventTriggered(ctx, evt);
    }
}
