package org.asynctaskmanager.core.domain.task;

import org.asynctaskmanager.core.domain.task.AsyncTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Package-local AsyncTask implementation.
 */
public class FutureBackedAsyncTask<T> implements AsyncTask<T> {

    private static final Logger logger = LoggerFactory.getLogger(FutureBackedAsyncTask.class);


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
            logger.warn("Task with id {} was interrupted", id);
        } catch (ExecutionException e) {
            logger.error("Task with id " + id + " was failed", e);
        }
        return null;
    }
}