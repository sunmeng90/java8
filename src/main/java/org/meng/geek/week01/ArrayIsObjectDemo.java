package org.meng.geek.week01;

// -XX:+TraceClassLoading -XX:+TraceClassUnloading # not working???

/**
 * array is also an object and it's class(created and extends Object).
 * All array of different length with same element has the same class
 */
public class ArrayIsObjectDemo {
    public static void main(String[] args) throws InterruptedException {
        String[] arr10 = new String[10];
        String[] arr20 = new String[20];
        assert arr10.getClass() != null;
        assert arr10.getClass() == arr20.getClass();
    }
}
