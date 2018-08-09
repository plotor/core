package org.zhenchao.demo;

import org.jetlang.channels.Channel;
import org.jetlang.channels.MemoryChannel;
import org.jetlang.core.Callback;
import org.jetlang.fibers.Fiber;
import org.jetlang.fibers.ThreadFiber;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author zhenchao.wang 2018-08-08 08:47
 * @version 1.0.0
 */
public class ChannelExample {

    public static void main(String[] args) throws Exception {
        // start thread backed receiver.
        // Lighweight fibers can also be created using a thread pool
        Fiber receiver = new ThreadFiber();
        receiver.start();

        // create java.util.concurrent.CountDownLatch to notify when message arrives
        final CountDownLatch latch = new CountDownLatch(1);

        // create channel to message between threads
        Channel<String> channel = new MemoryChannel<>();

        Callback<String> onMsg = new Callback<String>() {
            public void onMessage(String message) {
                //open latch
                System.out.println("message is coming and down count down : " + message);
                latch.countDown();
            }
        };

        //add subscription for message on receiver thread
        channel.subscribe(receiver, onMsg);

        //publish message to receive thread. the publish method is thread safe.
        channel.publish("Hello");

        //wait for receiving thread to receive message
        latch.await(10, TimeUnit.SECONDS);

        //shutdown thread
        receiver.dispose();
    }

}