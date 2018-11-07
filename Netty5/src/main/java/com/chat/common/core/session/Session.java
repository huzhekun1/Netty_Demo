package com.chat.common.core.session;

/**
 * 会话抽象接口
 * @author hzk
 * @date 2018/10/23
 */
public interface Session {

    /**
     * 会话绑定对象
     * @return
     */
    Object getAttachment();

    /**
     * 绑定会话对象
     */
    void setAttachment(Object attachment);

    /**
     * 移除会话对象
     */
    void removeAttachment();

    /**
     * 写入消息到会话
     *  @param msg 消息
     */
    void write(Object msg);

    /**
     * 判断会话是否在连接中
     * @return
     */
    boolean isConnected();

    /**
     * 关闭会话
     */
    void close();

}
