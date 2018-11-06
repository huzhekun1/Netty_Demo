package com.ithzk.constans;

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
         * 数据包基本长度
         * 4 + 2 +2 + 4
         */
        public static final int DATA_STRUCTURE_LENGTH = 12;
    }

    /**
     * 响应码相关
     */
    public abstract static class AbstractStateCode{
        /**
         * 失败
         */
        public static final int FAILURE = 0;

        /**
         * 成功
         */
        public static final int SUCCESS = 1;
    }

    /**
     * 模板相关(关卡)
     */
    public abstract static class AbstractModule{
        /**
         * 模拟第一关对应序列
         */
        public static final short ONE = 1;
        /**
         * 模拟第二关对应序列
         */
        public static final short TWO = 2;
    }

    /**
     * 命令号相关
     */
    public abstract static class AbstractCmd{
        /**
         * 命令号-1
         */
        public static final short ONE = 1;
        /**
         * 命令号-2
         */
        public static final short TWO = 2;

    }
}
