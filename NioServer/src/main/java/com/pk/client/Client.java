package com.pk.client;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * @author hzk
 * @date 2018/10/22
 */
public class Client {
    
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8888);
        String msg = "cliengggg";
        byte[] bytes = msg.getBytes();
        //由于要先缓存byte数组长度 需要一个int类型 所以需要加上4
        ByteBuffer allocate = ByteBuffer.allocate(4+bytes.length);
        allocate.putInt(bytes.length);
        allocate.put(bytes);

        byte[] array = allocate.array();
        for(int i =1;i<1000;i++){
            socket.getOutputStream().write(array);
        }
        socket.close();
    }
}
