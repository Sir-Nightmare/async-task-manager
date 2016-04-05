package org.asynctaskmanager.core.domain.task;

public interface AsyncTask<T> {
    String getId();
    boolean isDone();

    T getResult();
}
