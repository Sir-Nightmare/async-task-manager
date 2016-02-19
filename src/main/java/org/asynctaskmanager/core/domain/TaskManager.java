package org.asynctaskmanager.core.domain;


import java.util.Collection;

public interface TaskManager extends AutoCloseable {
    AsyncTask submit(Runnable todo);
    AsyncTask submit(String taskId, Runnable todo);

    AsyncTask getTask(String taskId);
    Collection<AsyncTask> getTasks();

    /**
     * Gracefully shutdowns TaskManager.
     * Tasks already in queue will be executed and active tasks continue to execute.
     * But new tasks aren't submitted.
     */
    void close() throws Exception;
}
