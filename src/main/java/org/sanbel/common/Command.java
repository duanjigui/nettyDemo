package org.sanbel.common;

/**
 * 指令
 * Created by duanjigui on 2019/1/21.
 */
public interface Command {

    public byte LOGIN=0x01;   //登录

    public byte LOGOUT=0x02; // 退出
    public byte PING=0x0E;   //ping消息
    public byte PONG=0x0F;  //pong消息


}
