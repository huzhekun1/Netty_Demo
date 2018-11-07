package com.chat.common.core.constants;

/**
 * 常量
 * @author hzk
 * @date 2018/9/29
 */
public class Constants {

    /**
     * netty配置相关
     */
    public abstract static class AbstractNettyConfig{
        /**
         * 端口
         */
        public static final int PORT = 8888;
        /**
         * IP
         */
        public static final String ADDRESS = "127.0.0.1";
    }

    /**
     * 自定义数据结构相关
     */
    public abstract static class AbstractDataStructure{
        /**
         * 包头
         */
        public static final int PACKAGE_HEAD = -37593513;
        /**
         * 数据包基本长度(Request)
         * 4 + 2 + 2 + 4
         */
        public static final int DATA_STRUCTURE_LENGTH = 12;
        /**
         * 数据包基本长度(Response) 比request多一个响应码(int) 之前在ResponseDecoder内部写死传递,现通过接收上一级传递解(译)码
         * 4 + 2 + 4 + 2 + 4
         */
        public static final int DATA_RESPONSE_STRUCTURE_LENGTH = 16;
    }

    /**
     * 模块相关
     */
    public abstract static class AbstractModule{
        /**
         * 用户/玩家模块
         */
        public static final short PLAYER = 1;
        /**
         * 聊天模块
         */
        public static final short CHAT = 2;
    }

    /**
     * 用户/玩家命令相关
     */
    public abstract static class AbstractCmdPlayer{
        /**
         * 创建并登陆
         */
        public static final short REGISTER_AND_LOGIN = 1;
        /**
         * 登陆
         */
        public static final short LOGIN = 2;
    }

    /**
     * 聊天命令相关
     */
    public abstract static class AbstractCmdChat{

        /**
         * 广播消息
         */
        public static final short PUBLIC_CHAT = 1;
        /**
         * 私人消息
         */
        public static final short PRIVATE_CHAT = 2;
        /**
         * 推送消息
         */
        public static final short PUSH_CHAT = 101;
    }

    /**
     * 聊天类型相关
     */
    public abstract static class AbstractChatType{
        /**
         * 广播消息
         */
        public static final byte PUBLIC_CHAT = 0;
        /**
         * 私人消息
         */
        public static final byte PRIVATE_CHAT = 1;
    }

}
