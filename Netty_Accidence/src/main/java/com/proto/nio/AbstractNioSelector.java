package com.proto.nio;

import com.proto.nio.pool.NioSelectorRunnablePool;

import java.io.IOException;
import java.nio.channels.Selector;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 抽象selector线程类
 * @author hzk
 * @date 2018/8/20
 */
public abstract class AbstractNioSelector implements Runnable{

    /**
     * 线程池
     */
    private final Executor executor;

    /**
     * 选择器
     */
    protected Selector selector;

    /**
     * 选择器wakenup的状态标记
     */
    protected final AtomicBoolean wakenUp = new AtomicBoolean();

    /**
     * 任务队列
     */
    private final Queue<Runnable> taskQueue = new ConcurrentLinkedDeque<Runnable>();

    /**
     * 线程名称
     */
    private String threadName;

    /**
     * 线程管理对象
     */
    protected NioSelectorRunnablePool nioSelectorRunnablePool;

    public AbstractNioSelector(Executor executor, String threadName, NioSelectorRunnablePool nioSelectorRunnablePool) {
        this.executor = executor;
        this.threadName = threadName;
        this.nioSelectorRunnablePool = nioSelectorRunnablePool;
        openSelector();
    }

    /**
     * 获取select并启动线程
     */
    private void openSelector(){
        try {
            this.selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create a selector.");
        }
        executor.execute(this);
    }

    @Override
    public void run() {
        Thread.currentThread().setName(this.threadName);
        while (true){
            try {
                wakenUp.set(false);

                select(this.selector);

                processTaskQueue();

                process(this.selector);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 注册一个任务并激活selector
     * @param task
     */
    protected final void registerTask(Runnable task){
        taskQueue.add(task);

        Selector selector = this.selector;

        if(null != selector){
            if(wakenUp.compareAndSet(false,true)){
                selector.wakeup();
            }
        }else{
            taskQueue.remove(task);
        }
    }

    /**
     * 执行队列里的任务
     */
    private void processTaskQueue(){
        for(;;){
            final Runnable task = taskQueue.poll();
            if(null == task){
                break;
            }
            task.run();
        }

    }

    /**
     * 获取线程管理对象
     * @return
     */
    public NioSelectorRunnablePool getNioSelectorRunnablePool() {
        return nioSelectorRunnablePool;
    }

    /**
     * select抽象方法
     * @param selector
     * @return
     * @throws IOException
     */
    protected abstract int select(Selector selector) throws IOException;

    /**
     * select业务处理
     * @param selector
     * @return
     * @throws IOException
     */
    protected abstract void process(Selector selector) throws IOException;
}
