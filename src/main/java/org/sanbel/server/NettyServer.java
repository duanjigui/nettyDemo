package org.sanbel.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import org.sanbel.codec.HeartBeatMessageEncode;
import org.sanbel.codec.ProtoctlDecoder;
import org.sanbel.heartBeat.HeartBeatHanlder;

import java.util.concurrent.TimeUnit;

/**
 * Created by duanjigui on 2019/1/21.
 */
public class NettyServer {

    private Integer port;

    public NettyServer(int port){
        this.port=port;
    }

    public void start(){
        NioEventLoopGroup bossGroup =new NioEventLoopGroup();
        NioEventLoopGroup workerGroup =new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap=new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.TCP_NODELAY,true)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ChannelPipeline pipeline= ch.pipeline();
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
                            pipeline.addLast(new LengthFieldPrepender(4));  //自动在整个包头部增加长度
                            pipeline.addLast(new IdleStateHandler(30,30,2, TimeUnit.SECONDS));
                            pipeline.addLast(new HeartBeatMessageEncode());
                            pipeline.addLast(new HeartBeatHanlder());
                            pipeline.addLast(new ProtoctlDecoder());
                            System.out.println(ch.remoteAddress());
                        }
                    });

            ChannelFuture future= bootstrap.bind(this.port).sync();
            future.channel().closeFuture().sync();
            System.out.println("启动成功，监听端口："+port);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
