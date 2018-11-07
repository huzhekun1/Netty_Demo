package com.chat.common.core.scanner;

import java.util.HashMap;
import java.util.Map;

/**
 * 命令执行器管理者
 * @author hzk
 * @date 2018/10/25
 */
public class InvokerHolder {

    /**
     * 命令调用器
     */
    private static Map<Short,Map<Short,Invoker>> invokers = new HashMap<>();

    /**
     * 添加命令调用器
     * @param module 模块号
     * @param cmd 命令号
     * @param invoker 调用器
     */
    public static void addInvoker(short module,short cmd,Invoker invoker){
        Map<Short, Invoker> invokerMap = invokers.get(module);
        if(null == invokerMap){
            invokerMap = new HashMap<>();
            invokers.put(module,invokerMap);
        }
        invokerMap.put(cmd,invoker);
    }

    /**
     * 获取命令调用器
     * @param module 模块号
     * @param cmd 命令号
     * @return {@link Invoker}
     */
    public static Invoker getInvoker(short module,short cmd){
        Map<Short, Invoker> invokerMap = invokers.get(module);
        if(null == invokerMap){
            return null;
        }
        return invokerMap.get(cmd);
    }
}
