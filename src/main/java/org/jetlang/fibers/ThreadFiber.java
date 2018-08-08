package org.jetlang.fibers;

import org.jetlang.core.Disposable;
import org.jetlang.core.RunnableExecutor;
import org.jetlang.core.RunnableExecutorImpl;
import org.jetlang.core.Scheduler;
import org.jetlang.core.SchedulerImpl;

import java.util.concurrent.TimeUnit;

/**
 * Fiber implementation backed by a dedicated thread for execution.
 */
public class ThreadFiber implements Fiber {

    private final Thread _thread;
    private final RunnableExecutor _queue;
    private final Scheduler _scheduler;

    /**
     * Create thread backed fiber
     *
     * @param queue - target queue
     * @param threadName - name to assign thread
     * @param isDaemonThread - true if daemon thread
     * @param scheduler - scheduler for delayed tasks
     */
    public ThreadFiber(RunnableExecutor queue, String threadName, boolean isDaemonThread, Scheduler scheduler) {
        _queue = queue;
        Runnable runThread = new Runnable() {
            @Override
            public void run() {
                _queue.run();
            }
        };
        _thread = this.createThread(threadName, runThread);
        _thread.setDaemon(isDaemonThread);
        _scheduler = scheduler;
    }

    public ThreadFiber(RunnableExecutor queue, String threadName, boolean isDaemonThread) {
        this(queue, threadName, isDaemonThread, new SchedulerImpl(queue));
    }

    private Thread createThread(String threadName, Runnable runThread) {
        if (threadName == null) {
            return new Thread(runThread);
        }
        return new Thread(runThread, threadName);
    }

    public ThreadFiber() {
        this(new RunnableExecutorImpl(), null, true);
    }

    public Thread getThread() {
        return _thread;
    }

    /**
     * Queue runnable for execution on this fiber.
     */
    @Override
    public void execute(Runnable command) {
        _queue.execute(command);
    }

    @Override
    public void add(Disposable runOnStop) {
        _queue.add(runOnStop);
    }

    @Override
    public boolean remove(Disposable disposable) {
        return _queue.remove(disposable);
    }

    @Override
    public int size() {
        return _queue.size();
    }

    @Override
    public void dispose() {
        _scheduler.dispose();
        _queue.dispose();
    }

    /**
     * Start thread
     */
    @Override
    public void start() {
        _thread.start();
    }

    /**
     * Wait for thread to complete
     */
    public void join() throws InterruptedException {
        _thread.join();
    }

    /**
     * Schedule a Runnable to execute in the future. Event will be executed on Fiber thread.
     */
    @Override
    public Disposable schedule(Runnable command, long delay, TimeUnit unit) {
        return _scheduler.schedule(command, delay, unit);
    }

    /**
     * Schedule recurring event. Event will be fired on fiber thread.
     */
    @Override
    public Disposable scheduleAtFixedRate(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        return _scheduler.scheduleAtFixedRate(command, initialDelay, delay, unit);
    }

    @Override
    public Disposable scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        return _scheduler.scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }

    @Override
    public String toString() {
        return "ThreadFiber{" + _thread + '}';
    }
}
