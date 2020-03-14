package org.meng.java.thread.db.pool;

import java.sql.Connection;
import java.util.LinkedList;

public class ConnectionPool {
    private LinkedList<Connection> pool = new LinkedList<>();

    public ConnectionPool(int initSize) {
        if (initSize > 0) {
            for (int i = 0; i < initSize; i++) {
                pool.addLast(ConnectionDriver.createConnection());
            }
        }
    }

    public void release(Connection connection) {
        if (connection != null) {
            synchronized (pool) {
                pool.addLast(connection);
                pool.notify(); // notify another thread
            }
        }
    }

    /**
     * Try to get a connection pool, and if timeout with milliseconds, then return null
     *
     * @param millis
     * @return
     * @throws InterruptedException
     */
    public Connection get(long millis) throws InterruptedException {
        synchronized (pool) {
            if (millis <= 0) {
                while (pool.isEmpty()) {
                    pool.wait();
                }
                return pool.removeFirst();
            } else {
                long future = System.currentTimeMillis() + millis;
                long remaining = millis;
                while (pool.isEmpty() && remaining > 0) {
                    pool.wait(remaining); // may be exit earlier than remaining
                    remaining = future - System.currentTimeMillis();
                }
                Connection result = null;
                if (!pool.isEmpty()) { // if there are available connection in pool, then return it
                    result = pool.removeFirst();
                }
                return result;
            }
        }
    }
}
