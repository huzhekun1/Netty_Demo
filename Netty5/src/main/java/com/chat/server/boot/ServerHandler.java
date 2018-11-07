package com.chat.server.boot;

import com.chat.common.core.constants.Constants;
import com.chat.common.core.constants.ResultCodeEnum;
import com.chat.common.core.model.Request;
import com.chat.common.core.model.Response;
import com.chat.common.core.model.Result;
import com.chat.common.core.scanner.Invoker;
import com.chat.common.core.scanner.InvokerHolder;
import com.chat.common.core.serializable.Serializable;
import com.chat.common.core.session.Session;
import com.chat.common.core.session.SessionImpl;
import com.chat.common.core.session.SessionManager;
import com.chat.common.entity.Player;
import com.google.protobuf.GeneratedMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 消息接收处理类
 * @author hzk
 * @date 2018/10/25
 */
public class ServerHandler extends SimpleChannelInboundHandler<Request>{

    /**
     * 接收消息
     * @param channelHandlerContext
     * @param request
     * @throws Exception
     */
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, Request request) throws Exception {
        handleMessage(new SessionImpl(channelHandlerContext.channel()),request);
    }

    private void handleMessage(Session session,Request request) throws Exception {
        Response response = new Response(request);

        System.out.println("ServerHandler->handleMessage Module:" + response.getModule() + ",Cmd:" + response.getCmd());

        //获取命令调用器
        Invoker invoker = InvokerHolder.getInvoker(request.getModule(), request.getCmd());
        if(null != invoker){
            try {
                Result result = null;
                //检测模块 若为用户模块 传入session channel参数，否则为聊天模块传入用户ID
                if(Constants.AbstractModule.PLAYER == request.getModule()){
                    result = (Result) invoker.invoke(session,request.getData());
                }else{
                    Object attachment = session.getAttachment();
                    if(null != attachment){
                        Player player = (Player) attachment;
                        result = (Result) invoker.invoke(player.getId(),request.getData());
                    }else{
                        //会话未登录 拒绝请求
                        response.setCode(ResultCodeEnum.NOT_LOGIN.getResultCode());
                        session.write(response);
                        return;
                    }
                }

                //检测请求状态
                if(ResultCodeEnum.SUCCESS.getResultCode() == result.getResultCode()){
                    //回写数据
                    Object content = result.getContent();
                    if(null != content){
                        if(content instanceof Serializable){
                            Serializable contentT = (Serializable) content;
                            response.setData(contentT.getBytes());
                        }else if(content instanceof GeneratedMessage){
                            GeneratedMessage contentT = (GeneratedMessage) content;
                            response.setData(contentT.toByteArray());
                        }else{
                            System.out.println(String.format("无法识别的传输对象:",content));
                        }
                    }
                    response.setCode(result.getResultCode());
                    session.write(response);
                }else{
                    //返回错误码
                    response.setCode(result.getResultCode());
                    session.write(response);
                    return;
                }
            }catch (Exception e){
                e.printStackTrace();
                //系统异常
                response.setCode(ResultCodeEnum.UNKNOWN_EXCEPTION.getResultCode());
                session.write(response);
            }
        }else{
            //未找到执行者
            response.setCode(ResultCodeEnum.NO_INVOKER.getResultCode());
            session.write(response);
            return;
        }
    }

    /**
     * 断线移除会话
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionImpl session = new SessionImpl(ctx.channel());
        Object attachment = session.getAttachment();
        if(null != attachment){
            Player player = (Player) attachment;
            SessionManager.removeSession(player.getId());
        }
    }


}
