package com.proto.nio;

import com.proto.nio.pool.NioSelectorRunnablePool;
import com.proto.nio.pool.Worker;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;

/**
 * worker实现
 * @author hzk
 * @date 2018/8/20
 */
public class NioServerWorker extends AbstractNioSelector implements Worker{

    public NioServerWorker(Executor executor, String threadName, NioSelectorRunnablePool nioSelectorRunnablePool) {
        super(executor, threadName, nioSelectorRunnablePool);
    }

    /**
     * 加入一个新的socket客户端
     * @param socketChannel
     */
    @Override
    public void registerNewChannelTask(final SocketChannel socketChannel) {
        final Selector selector = this.selector;
        registerTask(new Runnable() {
            @Override
            public void run() {
                try {
                    //将客户端socketChannel注册到selector中
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected int select(Selector selector) throws IOException {
        return selector.select(500);
    }

    @Override
    protected void process(Selector selector) throws IOException {
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        if(selectionKeys.isEmpty()){
            return;
        }
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while(iterator.hasNext()){
            SelectionKey key = iterator.next();
            //移除 防止重复处理
            iterator.remove();

            //得到事件发生的socket通道
            SocketChannel channel = (SocketChannel)key.channel();

            //数据总长度
            int ret = 0;
            boolean failure = true;
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //读取数据
            try {
                ret = channel.read(buffer);
                failure = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
            //判断连接是否断开
            if(ret <= 0 || failure){
                key.cancel();
                System.out.println("Client disconnect...");
            }else{
                System.out.println("Receive msg:"+ new String(buffer.array()));
                //回写数据给客户端
                ByteBuffer wrap = ByteBuffer.wrap("Receive success!".getBytes());
                channel.write(wrap);
            }

        }
    }
}
