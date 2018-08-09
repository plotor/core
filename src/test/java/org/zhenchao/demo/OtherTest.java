package org.zhenchao.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhenchao.wang 2018-08-09 09:21
 * @version 1.0.0
 */
public class OtherTest implements Runnable {

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            lock.lock();
            try {
                System.out.println("Thread-" + Thread.currentThread().getId() + " -> " + i);
                TimeUnit.SECONDS.sleep(1);
            } catch (Throwable e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Thread player1 = new Thread(new OtherTest());
        Thread player2 = new Thread(new OtherTest());
        player1.start();
        player2.start();
    }
}
