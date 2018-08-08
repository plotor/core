package org.zhenchao.demo;

import org.jetlang.channels.Channel;
import org.jetlang.channels.MemoryChannel;
import org.jetlang.core.Callback;
import org.jetlang.fibers.Fiber;
import org.jetlang.fibers.PoolFiberFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActorFactory {

    private final ExecutorService threads;

    private final PoolFiberFactory fiberFactory;

    public ActorFactory() {
        threads = Executors.newCachedThreadPool();
        fiberFactory = new PoolFiberFactory(threads);
    }

    public Fiber startFiber() {
        Fiber fiber = fiberFactory.create();
        fiber.start();
        return fiber;
    }

    public <T> Channel<T> createInbox() {
        return new MemoryChannel<>();
    }

    public <T> Actor<T> createActor(Callback<T> actorCallback) {
        Fiber fiber = this.startFiber();
        Channel<T> inbox = this.createInbox();
        inbox.subscribe(fiber, actorCallback);
        return new Actor<>(fiber, inbox, actorCallback);
    }
}
