package org.asynctaskmanager.core.domain.task;

import org.asynctaskmanager.core.domain.task.AsyncTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Package-local AsyncTask implementation.
 */
public class FutureBackedAsyncTask<T> implements AsyncTask<T> {
    private final Future<T> result;
    private final String id;

    public FutureBackedAsyncTask(String id, Future<T> result) {
        this.result = result;
        this.id = id;
    }

    public FutureBackedAsyncTask(Future<T> result) {
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

    @Override
    public T getResult() {
        if (!isDone())  {
            return null;
        }
        try {
            return result.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}