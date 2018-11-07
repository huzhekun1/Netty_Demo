package com.proto.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 传统IO
 * @author hzk
 * @date 2018/8/14
 */
public class OioServer {

    private static Integer port = 10010;

    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Socket server start...");
        while(true){
            //获取一个套接字(阻塞)
            final Socket accept = serverSocket.accept();
            System.out.println("Have new client into...");
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    handler(accept);
                }
            });
        }
    }

    /**
     * 读取数据
     * @param socket
     */
    public static void handler(Socket socket){
        try {
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            while(true){
                //读取数据
                int read = inputStream.read(bytes);
                if(-1 != read){
                    System.out.println(new String(bytes,0,read));
                }else{
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                System.out.println("Socket server close...");
                socket.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
