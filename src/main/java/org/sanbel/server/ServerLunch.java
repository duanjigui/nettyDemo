package org.sanbel.server;

/**
 * 服务端启动
 * Created by duanjigui on 2019/1/21.
 */
public class ServerLunch {


    public static void main(String[] args) {
        NettyServer server=new NettyServer(8000);
        server.start();
    }

}
