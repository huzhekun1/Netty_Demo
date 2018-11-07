package com.chat.client.boot;

import com.chat.client.swing.SwingClient;
import com.chat.common.core.model.Response;
import com.chat.common.core.scanner.Invoker;
import com.chat.common.core.scanner.InvokerHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 消息接收处理类
 * @author hzk
 * @date 2018/10/25
 */
public class ClientHandler extends SimpleChannelInboundHandler<Response> {

    /**
     * 界面客户端
     */
    private SwingClient swingClient;

    /**
     * 接收消息
     * @param channelHandlerContext
     * @param response
     * @throws Exception
     */
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, Response response) throws Exception {
        handleMessage(response);
    }


    public ClientHandler(SwingClient swingClient) {
        this.swingClient = swingClient;
    }

    /**
     * 消息处理
     * @param response {@link Response}
     */
    private void handleMessage(Response response){
        System.out.println("ServerHandler->handleMessage Module:" + response.getModule() + ",Cmd:" + response.getCmd());
        //获取命令执行器
        Invoker invoker = InvokerHolder.getInvoker(response.getModule(), response.getCmd());
        if(null != invoker){
            try {
                invoker.invoke(response.getCode(),response.getData());
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            //未找到执行器
            System.out.println("未找到执行器!");
        }

    }

    /**
     * 断开连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        swingClient.getHints().setText("与服务器断开连接!");
    }

}
