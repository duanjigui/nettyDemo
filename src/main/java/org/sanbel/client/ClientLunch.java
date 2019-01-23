package org.sanbel.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.sanbel.codec.HeartBeatMessageEncode;
import org.sanbel.codec.ProtoctlDecoder;
import org.sanbel.codec.ProtoctlEncoder;
import org.sanbel.common.LoginPacket;
import org.sanbel.heartBeat.HeartBeatMessage;

/**
 * 客户端启动
 * Created by duanjigui on 2019/1/21.
 */
public class ClientLunch {

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap=new Bootstrap();
        bootstrap.channel(NioSocketChannel.class)
                .group(new NioEventLoopGroup())
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        final ChannelPipeline pipeline= ch.pipeline();
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4)); //第四个参数式跳过n个子接开始解析
                        pipeline.addLast(new LengthFieldPrepender(4));  //自动在整个包头部增加长度
                        pipeline.addLast(new ProtoctlEncoder());
                        pipeline.addLast(new HeartBeatMessageEncode());
                        pipeline.addLast(new ProtoctlDecoder());
                        pipeline.addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                LoginPacket packet =new LoginPacket();
                                ctx.writeAndFlush(packet);
                            }

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                if (msg instanceof ByteBuf){
                                    ByteBuf buf= (ByteBuf) msg;
                                    int length=buf.readableBytes();
                                    byte[] b= new byte[length];
                                    buf.readBytes(b);
                                    System.out.println("接收到消息: "+new String(b));
                                }
                                if (msg instanceof HeartBeatMessage){
                                    HeartBeatMessage m= (HeartBeatMessage) msg;
                                    System.err.println("收到心跳消息:  "+m);
                                    HeartBeatMessage pong =new HeartBeatMessage(HeartBeatMessage.PONG);
                                    ctx.writeAndFlush(pong);
                                }

                                super.channelRead(ctx, msg);
                            }
                        });


                    }
                });
        ChannelFuture future= bootstrap.connect("127.0.0.1",8000).sync();
        future.channel().closeFuture().sync();
    }


}
