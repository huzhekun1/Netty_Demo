package com.chat.common.core.scanner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 命令执行器/调用器
 * @author hzk
 * @date 2018/10/25
 */
public class Invoker {

    /**
     * 方法
     */
    private Method method;

    /**
     * 目标对象
     */
    private Object target;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Invoker(Method method, Object target) {
        this.method = method;
        this.target = target;
    }

    /**
     * 执行
     * @param paramValues
     * @return
     */
    public Object invoke(Object... paramValues){
        try {
            return method.invoke(target,paramValues);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Invoker valueOf(Method method,Object target){
        return new Invoker(method,target);
    }

}
