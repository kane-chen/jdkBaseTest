package net.jcip.examples.chap4;

import net.jcip.annotations.*;
import net.jcip.examples.chap2.Widget;
/**
 * PrivateLock
 * <p/>
 * Guarding state with a private lock
 *
 * @author Brian Goetz and Tim Peierls
 */
public class PrivateLock {
    private final Object myLock = new Object();
    @GuardedBy("myLock") 
    Widget widget;

    void someMethod() {
        synchronized (myLock) {
            // Access or modify the state of widget
        }
    }
}
