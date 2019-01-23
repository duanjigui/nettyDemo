package org.sanbel.heartBeat;

/**
 * 心跳消息
 * Created by duanjigui on 2019/1/23.
 */
public class HeartBeatMessage {

    private byte type;  //消息类型

    private long timestamp; //时间戳

    private int status;  //状态

    private String desc;  //描述

    public static final byte PING=0x01;  // ping 消息

    public static final byte PONG=0x02;  // pong消息

    public HeartBeatMessage() {
    }

    public HeartBeatMessage(byte type) {
        this(type,CodeEnum.SUCCESS);
    }

    public HeartBeatMessage(byte type,CodeEnum codeEnum) {
        this(type,System.currentTimeMillis(),codeEnum.code,codeEnum.desc);
    }

    public HeartBeatMessage(byte type, long timestamp, int status, String desc) {
        this.type = type;
        this.timestamp = timestamp;
        this.status = status;
        this.desc = desc;
    }

    public static enum CodeEnum{
        SUCCESS(200,"成功"),FAIL(500,"失败");

        private int code;
        private String desc;

        CodeEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

    }

    public byte getType() {
        return type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }


    public void setType(byte type) {
        this.type = type;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "HeartBeatMessage{" +
                "type=" + type +
                ", timestamp=" + timestamp +
                ", status=" + status +
                ", desc='" + desc + '\'' +
                '}';
    }

}
