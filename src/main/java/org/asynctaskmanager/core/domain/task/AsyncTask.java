package org.asynctaskmanager.core.domain.task;

/**
 * Response task
 *
 * @param <T>
 */
public interface AsyncTask<T> {
    String getId();
    boolean isDone();

    /**
     * return result of execution future
     *
     * @return if isDone() == true -> future.get() else null
     */
    T getResult();
}
