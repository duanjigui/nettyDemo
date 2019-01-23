package org.sanbel.common;

/**
 * 序列化器
 * Created by duanjigui on 2019/1/21.
 */
public interface Serializer {

    /**
     * 对象转换为byte数组
     * @param object
     * @return
     */
    public byte[] encode(Object object);

    /**
     *解码数组为对象
     * @param data
     * @return
     */
    public Object decode(byte[] data);

}
