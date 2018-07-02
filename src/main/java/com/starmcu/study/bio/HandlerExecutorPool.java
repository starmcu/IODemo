package com.starmcu.study.bio;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HandlerExecutorPool {

    private ExecutorService executor =null;



    public  HandlerExecutorPool(int maxSize,int queueSize){
        this.executor =new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),maxSize,
                120L,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(queueSize));
    }

    public void executor(Runnable task){
        executor.execute(task);
    }

}
