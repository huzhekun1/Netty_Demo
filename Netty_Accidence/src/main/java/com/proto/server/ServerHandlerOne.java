package com.proto.server;

import org.jboss.netty.channel.*;

/**
 * 消息接受处理类
 * @author hzk
 * @date 2018/8/16
 */
public class ServerHandlerOne extends SimpleChannelHandler {

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        //接收数据
        //ChannelBuffer channelBuffer = (ChannelBuffer)e.getMessage();
        //System.out.println("Receive:"+new String(channelBuffer.array()));
        //decoder
        System.out.println((String)e.getMessage());

        //回写数据
        //encoder
        ctx.getChannel().write("ok!");
        super.messageReceived(ctx, e);
    }

    /**
     * 捕获异常
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        System.out.println("exceptionCaught");
        super.exceptionCaught(ctx, e);
    }

    /**
     * 新连接 通常用来检测IP是否是黑名单
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("channelConnected");
        super.channelConnected(ctx, e);
    }

    /**
     * 必须是连接已经建立，关闭通道的时候才会触发,可以在用户断线的时候清除用户的缓存数据等(只有在连接建立后断开才会调用)
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("channelDisconnected");
        super.channelDisconnected(ctx, e);
    }

    /**
     * channel关闭的时候触发(无论连接是否成功都会调用关闭资源)
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("channelClosed");
        super.channelClosed(ctx, e);
    }
}
