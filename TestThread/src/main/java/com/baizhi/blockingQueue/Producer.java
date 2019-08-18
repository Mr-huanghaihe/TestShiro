package com.baizhi.blockingQueue;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @program: untitled
 * @description:
 * @author: Mr.huang
 * @create: 2019-08-18 10:44
 */
//生产者
public class Producer implements Runnable {
    private final BlockingQueue<Integer> queue;
    public Producer(BlockingQueue q) {
        this.queue = q;
    }
    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(1000);//模拟耗时
                queue.put(produce());
            }
        } catch (InterruptedException e) {
        }
    }
    private int produce() {
        int n = new Random().nextInt(10000);
        System.out.println("Thread:" + Thread.currentThread().getId() + " produce:" + n);
        return n;
    }
}
//消费者
class Consumer implements Runnable {
    private final BlockingQueue<Integer> queue;

    public Consumer(BlockingQueue q) {
        this.queue = q;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(2000);//模拟耗时
                consume(queue.take());
            } catch (InterruptedException e) {
            }
        }
    }
    private void consume(Integer n) {
        System.out.println("Thread:" + Thread.currentThread().getId() + " consume:" + n);
    }
}
//测试
class Main {
    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(100);
        Producer p = new Producer(queue);
        Consumer c1 = new Consumer(queue);
        Consumer c2 = new Consumer(queue);
        new Thread(p).start();
        new Thread(c1).start();
        new Thread(c2).start();
    }
}
