package com.chat.client.boot;

import com.chat.client.swing.SwingClient;
import com.chat.server.boot.Server;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Netty服务端启动类
 * @author hzk
 * @date 2018/10/25
 */
public class ClientMain {
    
    public static void main(String[] args){
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("client/application.xml");
        SwingClient swingClient = classPathXmlApplicationContext.getBean(SwingClient.class);
        swingClient.setVisible(true);
    }
}
