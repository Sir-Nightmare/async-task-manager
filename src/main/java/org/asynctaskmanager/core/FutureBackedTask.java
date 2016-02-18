package org.asynctaskmanager.core;

import java.util.concurrent.Future;

/**
 * Package-local Task implementation.
 */
//FIXME id
class FutureBackedTask implements Task {
    private final Future<?> result;
    private volatile String id;

    public FutureBackedTask(String id, Future<?> result) {
        this(result);
        this.id = id;
    }

    public FutureBackedTask(Future<?> result) {
        this.result = result;
        this.id = String.valueOf(System.identityHashCode(this));
    }


    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public boolean isDone() {
        return result.isDone();
    }
}