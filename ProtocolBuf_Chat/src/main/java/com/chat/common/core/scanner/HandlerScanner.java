package com.chat.common.core.scanner;

import com.chat.common.core.annotation.SocketCommand;
import com.chat.common.core.annotation.SocketModule;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Handler扫描器
 * @author hzk
 * @date 2018/10/25
 */
@Component
public class HandlerScanner implements BeanPostProcessor{

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<? extends Object> clazz = bean.getClass();

        Class<?>[] interfaces = clazz.getInterfaces();
        if(null != interfaces && interfaces.length >0){
            //扫描类的所有接口父类
            for (Class<?> anInterface : interfaces) {
                //判断是否为Handler接口类
                SocketModule moduleAnnotation = anInterface.getAnnotation(SocketModule.class);
                if(null == moduleAnnotation){
                    continue;
                }

                //找出命令方法
                Method[] methods = anInterface.getMethods();
                if(null != methods && methods.length > 0){
                    for (Method method : methods) {
                        SocketCommand cmdAnnotation = method.getAnnotation(SocketCommand.class);
                        if(null == cmdAnnotation){
                            continue;
                        }

                        short module = moduleAnnotation.module();
                        short cmd = cmdAnnotation.cmd();
                        
                        if(null == InvokerHolder.getInvoker(module,cmd)){
                            InvokerHolder.addInvoker(module,cmd,Invoker.valueOf(method,bean));
                        }else{
                            System.out.println("Repeat Module:" + module + ",Cmd:" + cmd);
                        }
                    }
                }
            }
        }

        return bean;
    }
}
