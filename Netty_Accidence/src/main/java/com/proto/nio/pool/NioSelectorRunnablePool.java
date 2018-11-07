package com.proto.nio.pool;

import com.proto.nio.NioServerBoss;
import com.proto.nio.NioServerWorker;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * selector线程管理者
 * @author hzk
 * @date 2018/8/20
 */
public class NioSelectorRunnablePool {

    /**
     * boos线程数组
     */
    private final AtomicInteger bossIndex = new AtomicInteger();
    private Boss[] bosses;

    /**
     * worker线程数组
     */
    private final AtomicInteger workerIndex = new AtomicInteger();
    private Worker[] workers;

    public NioSelectorRunnablePool(Executor boss, Executor worker) {
        initBoss(boss, 1);
        initWorker(worker, Runtime.getRuntime().availableProcessors() * 2);
    }

    /**
     * 初始化boss线程
     * @param boss
     * @param count
     */
    private void initBoss(Executor boss, int count){
        this.bosses = new NioServerBoss[count];
        for (int i = 0 ;i < bosses.length;i++){
            bosses[i] = new NioServerBoss(boss,"Boss_Thread_"+(i+1),this);
        }
    }

    /**
     * 初始化worker线程
     * @param worker
     * @param count
     */
    private void initWorker(Executor worker, int count){
        this.workers = new NioServerWorker[count];
        for (int i = 0 ;i < workers.length;i++){
            workers[i] = new NioServerWorker(worker,"Worker_Thread_"+(i+1),this);
        }
    }

    /**
     * 获取一个Boss
     * @return
     */
    public Boss nextBosses() {
        return bosses[Math.abs(bossIndex.getAndIncrement() % bosses.length)];
    }

    /**
     * 获取一个Worker
     * @return
     */
    public Worker nextWorkers() {
        return workers[Math.abs(workerIndex.getAndIncrement() % workers.length)];
    }

}
