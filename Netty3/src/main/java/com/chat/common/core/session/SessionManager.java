package com.chat.common.core.session;

import com.chat.common.core.constants.ResultCodeEnum;
import com.chat.common.core.model.Response;
import com.chat.common.core.serializable.Serializable;
import com.google.protobuf.GeneratedMessage;
import org.jboss.netty.util.internal.ConcurrentHashMap;

import java.util.Collections;
import java.util.Set;

/**
 * 会话管理者
 * @author hzk
 * @date 2018/10/23
 */
public class SessionManager {

    /**
     * 在线会话组
     */
    private static final ConcurrentHashMap<Long,Session> onlineSessions = new ConcurrentHashMap<>();

    /**
     * 将当前用户加入在线会话组
     * @param playerId 用户ID
     * @param session 当前用户会话
     * @return 是否加入成功
     */
    public static boolean putSession(long playerId,Session session){
        if(!onlineSessions.contains(playerId)){
            //如果传入key对应的value已经存在，就返回存在的value，不进行替换。如果不存在，就添加key和value，返回null
            return onlineSessions.putIfAbsent(playerId, session) == null ? true : false;
        }
        return false;
    }

    /**
     * 移除当前用户
     * @param playerId 用户ID
     * @return 是否移除成功
     */
    public static Session removeSession(long playerId){
        return onlineSessions.remove(playerId);
    }

    /**
     * 发送消息[自定义协议]
     * @param playerId 用户ID
     * @param module 模块号
     * @param cmd 命令号
     * @param message 发送消息
     * @param <T> 发送消息泛型
     */
    public static <T extends Serializable> void sendMessage(long playerId,short module,short cmd,T message){
        Session session = onlineSessions.get(playerId);
        if(null != session && session.isConnected()){
            Response response = new Response(module, cmd, message.getBytes(), ResultCodeEnum.SUCCESS.getResultCode());
            session.write(response);
        }
    }

    /**
     * 发送消息[Protocol Buffers协议]
     * @param playerId 用户ID
     * @param module 模块号
     * @param cmd 命令号
     * @param message 发送消息
     * @param <T> 发送消息泛型
     */
    public static <T extends GeneratedMessage> void sendMessage(long playerId,short module,short cmd,T message){
        Session session = onlineSessions.get(playerId);
        if(null != session && session.isConnected()){
            Response response = new Response(module, cmd, message.toByteArray(), ResultCodeEnum.SUCCESS.getResultCode());
            session.write(response);
        }
    }

    /**
     * 检测用户是否在线
     * @param playerId 用户ID
     * @return
     */
    public static boolean isOnlinePlayer(long playerId){
        return onlineSessions.containsKey(playerId);
    }

    /**
     * 获取所有在线用户ID
     * @return
     */
    public static Set<Long> getOnlinePlayers(){
        return Collections.unmodifiableSet(onlineSessions.keySet());
    }
}
