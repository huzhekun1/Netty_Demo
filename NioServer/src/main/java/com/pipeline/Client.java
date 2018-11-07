package com.pipeline;

import java.io.IOException;
import java.net.Socket;

/**
 * @author hzk
 * @date 2018/10/22
 */
public class Client {
    
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8888);
        socket.getOutputStream().write("clientgogogo".getBytes());
        socket.close();
    }
}
