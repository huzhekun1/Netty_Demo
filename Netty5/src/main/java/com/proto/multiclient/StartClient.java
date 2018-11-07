package com.proto.multiclient;

import io.netty.channel.Channel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author hzk
 * @date 2018/9/18
 */
public class StartClient {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 8888;

    public static void main(String[] args){
        MultiClient multiClient = new MultiClient(ADDRESS,PORT, 5);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            try {
                System.out.println("Please input:");
                String msg = bufferedReader.readLine();
//                multiClient.nextChannel(ADDRESS,PORT).writeAndFlush(msg);
                Channel channel = multiClient.nextChannel(ADDRESS, PORT);
                channel.writeAndFlush(msg+":"+multiClient.indexChannel(channel));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
