package com.proto.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * 非阻塞IO new io
 * @author hzk
 * @date 2018/8/15
 */
public class NioServer {

    /**
     * 通道管理器
     */
    private Selector selector;

    public void initServer(int port) throws IOException {
        //获取一个ServerSocket通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //将该通道对应的serverSocket绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        //获取一个通道管理器
        selector = Selector.open();
        //将通道管理器和该通道绑定，并向该通道注册SelectionKey.OP_ACCEPT事件，注册该事件后当该事件出发到达时
        //selector.select()会返回,如果未到达会一直阻塞
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    /**
     * 采用轮询的方式监听selector上是否有需要处理的事件，如果有则进行处理
     * @throws IOException
     */
    public void listen() throws IOException {
        System.out.println("Server start success...");
        //轮询访问selecor
        while(true){
            //当注册的事件到达时，方法返回，否则一直阻塞
            int select = selector.select();
            //获取selector选中项的迭代器，选中项为注册的事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey next = iterator.next();
                //删除已选的key,已防重复处理
                iterator.remove();
                handler(next);
            }
        }
    }

    /**
     * 处理请求
     * @param selectionKey
     * @throws IOException
     */
    public void handler(SelectionKey selectionKey) throws IOException {
        //客户端请求连接事件
        if(selectionKey.isAcceptable()){
            handlerAceept(selectionKey);
        }else if(selectionKey.isReadable()){
            //获得了可读事件
            handlerRead(selectionKey);
        }
    }

    /**
     * 处理连接请求
     * @param selectionKey
     * @throws IOException
     */
    public void handlerAceept(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel channel = (ServerSocketChannel)selectionKey.channel();
        //获取客户端连接的通道
        SocketChannel accept = channel.accept();
        //设置非阻塞
        accept.configureBlocking(false);
        //此处可给客户端发送信息
        System.out.println("Have new client into....");
        //在和客户端连接成功之后，为了可以接收到客户端的信息，需要给通道设置读的权限
        accept.register(selector,SelectionKey.OP_READ);
    }

    /**
     * 处理读事件
     * @param selectionKey
     * @throws IOException
     */
    public void handlerRead(SelectionKey selectionKey) throws IOException {
        //服务器可读取消息:得到事件发生的Socket通道
        SocketChannel channel = (SocketChannel)selectionKey.channel();
        //创建读取的缓冲区
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        int read = channel.read(allocate);
        if(read >0){
            byte[] array = allocate.array();
            String msg = new String(array).trim();
            System.out.println("Server receive msg:"+msg);
            //回写数据
            ByteBuffer wrap = ByteBuffer.wrap("ok!".getBytes());
            //将消息回送给客户端
            channel.write(wrap);
        }else{
            System.out.println("Client close...");
            channel.close();
        }
    }

    /**
     * telnet之后全黑 ctrl+]可以调出命令输入行
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        NioServer nioServer = new NioServer();
        nioServer.initServer(8888);
        nioServer.listen();
    }


}
