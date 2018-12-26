package com.demo.thread;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Author Elvis Chen
 * @Date 2018/12/26 14:29
 * @Version 1.0
 **/
@Slf4j
public class MapExample {

    private static Map<Integer, Integer> map =Maps.newHashMap();

    private static int threadNum = 200;
    private static int clientNum = 5000;

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadNum);
        for (int index = 0; index < clientNum; index++){
            final int threadNum = index;
            exec.execute(() -> {
                try {
                   semaphore.acquire();
                   func(threadNum);
                   semaphore.release();
                } catch (Exception e){
                    log.error("exception", e);
                }
            });
        }
        exec.shutdown();
        log.info("size:{}", map.size());
    }

    public static void func(int threadNum) {
        map.put(threadNum, threadNum);
    }


}
