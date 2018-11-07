package com.chat.client.swing;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 消息提示
 * @author hzk
 * @date 2018/10/25
 */
@Component
public class ResultCodeHint {

    private Properties properties= new Properties();

    /**
     * 初始化读取配置文件
     * @throws IOException
     */
    @PostConstruct
    public void init() throws IOException {
        InputStream in = getClass().getResourceAsStream("/client/code.properties");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        properties.load(bufferedReader);
    }

    /**
     * 错误内容提示
     * @param code 错误码
     * @return
     */
    public String getHintContent(int code){
        Object object = properties.get(code+"");
        if(null == object){
            return "错误码:" + code;
        }
        return object.toString();
    }

}
