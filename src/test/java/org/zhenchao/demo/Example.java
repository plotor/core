package org.zhenchao.demo;

import org.jetlang.fibers.Fiber;
import org.jetlang.fibers.ThreadFiber;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author zhenchao.wang 2018-08-08 08:44
 * @version 1.0.0
 */
public class Example {

    public static void main(String[] args) throws Exception {
        Fiber fiber = new ThreadFiber();
        fiber.start();
        final CountDownLatch latch = new CountDownLatch(2);
        Runnable toRun = new Runnable() {
            public void run() {
                System.out.println("Thread-" + Thread.currentThread().getId() + " do count down.");
                latch.countDown();
            }
        };
        //enqueue runnable for execution
        fiber.execute(toRun);
        //repeat to trigger latch a 2nd time
        fiber.execute(toRun);
        latch.await(10, TimeUnit.SECONDS);
        //shutdown thread
        fiber.dispose();
    }

}
