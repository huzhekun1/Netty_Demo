package com.chat.server.boot;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Netty服务端启动类
 * @author hzk
 * @date 2018/10/25
 */
public class ServerMain {
    
    public static void main(String[] args){
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("server/application.xml");
        Server server = classPathXmlApplicationContext.getBean(Server.class);
        server.start();
    }
}
