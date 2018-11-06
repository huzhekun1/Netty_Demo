package com.ithzk.client;

import com.ithzk.constans.Constants;
import com.ithzk.model.Response;
import com.ithzk.module.customspass.response.FightResponse;
import org.jboss.netty.channel.*;

/**
 * 消息接收处理类
 * @author hzk
 * @date 2018/10/8
 */
public class ClientHandler extends SimpleChannelHandler{

    /**
     * 接收消息
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Response response = (Response) e.getMessage();
        if(Constants.AbstractModule.ONE == response.getModule()){
            if(Constants.AbstractCmd.ONE == response.getCmd()){
                FightResponse fightResponse = new FightResponse();
                fightResponse.readFromBytes(response.getData());

                System.out.println("ClientHandler->messageReceived:"+fightResponse);
            }
        }else if(Constants.AbstractModule.TWO == response.getModule()){
            System.out.println("CMD:"+Constants.AbstractCmd.TWO);
        }

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
     * 新连接
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
     * 必须是连接已经建立，关闭通道的时候才会触发
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
     * channel关闭的时候触发
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
