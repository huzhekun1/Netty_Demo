package com.proto.server;

import org.jboss.netty.channel.*;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateEvent;

import java.util.Date;

/**
 * @author hzk
 * @date 2018/9/19
 */
public class ServerHeartHandler2 extends SimpleChannelHandler{

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        System.out.println(e.getMessage());
    }

    @Override
    public void handleUpstream(final ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if(e instanceof IdleStateEvent){
            System.out.println(new Date(System.currentTimeMillis())+":"+((IdleStateEvent) e).getState());
            if(((IdleStateEvent)e).getState() == IdleState.ALL_IDLE){
                System.out.println("踢用户下线!");
                //关闭会话T玩家下线
                ChannelFuture channelFuture = ctx.getChannel().write("Time out,you will close!");
                channelFuture.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        ctx.getChannel().close();
                    }
                });
            }
        }else{
            super.handleUpstream(ctx,e);
        }
    }
}
