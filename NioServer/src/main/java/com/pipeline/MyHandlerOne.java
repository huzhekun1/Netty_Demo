package com.pipeline;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.*;

/**
 * @author hzk
 * @date 2018/10/22
 */
public class MyHandlerOne extends SimpleChannelHandler{

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        ChannelBuffer channelBuffer = (ChannelBuffer) e.getMessage();
        byte[] array = channelBuffer.array();
        String msg = new String(array);
        System.out.println("Handler One:"+msg);

        //传递
        ctx.sendUpstream(new UpstreamMessageEvent(ctx.getChannel(),"abc",e.getRemoteAddress()));
        ctx.sendUpstream(new UpstreamMessageEvent(ctx.getChannel(),"efg",e.getRemoteAddress()));
    }
}
