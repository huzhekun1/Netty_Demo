package com.chat.common.core.session;


import org.jboss.netty.channel.Channel;

/**
 * 会话实现封装类
 * @author hzk
 * @date 2018/10/23
 */
public class SessionImpl implements Session{

    /**
     * 实际会话对象
     */
    private Channel channel;

    public SessionImpl(Channel channel) {
        this.channel = channel;
    }

    @Override
    public Object getAttachment() {
        return channel.getAttachment();
    }

    @Override
    public void setAttachment(Object attachment) {
        channel.setAttachment(attachment);
    }

    @Override
    public void removeAttachment() {
        channel.setAttachment(null);
    }

    @Override
    public void write(Object msg) {
        channel.write(msg);
    }

    @Override
    public boolean isConnected() {
        return channel.isConnected();
    }

    @Override
    public void close() {
        channel.close();
    }
}
