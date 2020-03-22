package org.meng.java.thread.pool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

class DefaultWorkerThreadFactory implements ThreadFactory {

    private final AtomicLong threadSeq = new AtomicLong();

    public DefaultWorkerThreadFactory() {
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, "DefaultThreadPool-Worker-" + threadSeq.incrementAndGet());
    }
}
