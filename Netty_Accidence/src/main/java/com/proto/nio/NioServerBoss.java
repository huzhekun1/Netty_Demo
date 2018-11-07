package com.proto.nio;

import com.proto.nio.pool.Boss;
import com.proto.nio.pool.NioSelectorRunnablePool;
import com.proto.nio.pool.Worker;

import java.io.IOException;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;

/**
 * Boss实现
 * @author hzk
 * @date 2018/8/20
 */
public class NioServerBoss extends AbstractNioSelector implements Boss{

    public NioServerBoss(Executor executor,String threadName, NioSelectorRunnablePool nioSelectorRunnablePool) {
        super(executor,threadName, nioSelectorRunnablePool);
    }

    @Override
    public void regiserAcceptChannelTask(final ServerSocketChannel serverSocketChannel) {
        final Selector selector = this.selector;
        registerTask(new Runnable() {
            @Override
            public void run() {
                try {
                    //注册serverChannel到selector
                    serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected int select(Selector selector) throws IOException {
        return selector.select();
    }

    @Override
    protected void process(Selector selector) throws IOException {
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        if(selectionKeys.isEmpty()){
            return;
        }
        for (Iterator<SelectionKey> iterator = selectionKeys.iterator(); iterator.hasNext();){
            SelectionKey key = iterator.next();
            iterator.remove();
            ServerSocketChannel server = (ServerSocketChannel)key.channel();
            //新客户端
            SocketChannel channel = server.accept();
            //设置为非阻塞
            channel.configureBlocking(false);
            //获取一个worker
            Worker worker = getNioSelectorRunnablePool().nextWorkers();
            //注册新客户端接入任务
            worker.registerNewChannelTask(channel);
            System.out.println("New Client into...");
        }

    }
}
