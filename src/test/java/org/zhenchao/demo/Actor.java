package org.zhenchao.demo;

import org.jetlang.channels.Channel;
import org.jetlang.core.Callback;
import org.jetlang.fibers.Fiber;

public class Actor<T> {

    /** a way to pass messages between Fibers */
    private final Channel<T> inbox;

    /** a lightweight thread-like construct */
    private final Fiber fiber;

    /** a function to be invoked when a message arrives on a Channel in the context of a Fiber */
    private final Callback<T> callbackFunction;

    public Actor(Fiber fiber, Channel<T> inbox, Callback<T> callbackFunction) {
        this.fiber = fiber;
        this.inbox = inbox;
        this.callbackFunction = callbackFunction;
    }

    public Channel<T> inbox() {
        return this.inbox;
    }

    public Fiber fiber() {
        return this.fiber;
    }

    public Callback<T> callbackFunction() {
        return this.callbackFunction;
    }

    public void send(T message) {
        this.inbox.publish(message);
    }
}
