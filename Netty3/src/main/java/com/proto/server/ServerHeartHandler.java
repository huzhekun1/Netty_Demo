package com.proto.server;

import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hzk
 * @date 2018/9/19
 */
public class ServerHeartHandler extends IdleStateAwareChannelHandler implements ChannelHandler{

    @Override
    public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss");
        //会话状态
        System.out.println(e.getState()+":"+simpleDateFormat.format(new Date()));
    }
}
