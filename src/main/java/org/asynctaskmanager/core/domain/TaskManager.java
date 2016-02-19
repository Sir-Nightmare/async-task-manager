package org.asynctaskmanager.core.domain;


import java.util.Collection;

public interface TaskManager extends AutoCloseable {
    AsyncTask submit(Runnable todo) throws TaskAlreadySubmittedException;
    AsyncTask submit(String taskId, Runnable todo) throws TaskAlreadySubmittedException;

    AsyncTask getTask(String taskId);
    Collection<AsyncTask> getTasks();

    /**
     * Gracefully shutdowns TaskManager.
     * Tasks already in queue will be executed and active tasks continue to execute.
     * But new tasks aren't submitted.
     */
    void close() throws Exception;
}
