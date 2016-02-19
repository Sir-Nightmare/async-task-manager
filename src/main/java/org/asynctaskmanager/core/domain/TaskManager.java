package org.asynctaskmanager.core.domain;


public interface TaskManager extends AutoCloseable {
    AsyncTask submit(Runnable todo);
    AsyncTask submit(String taskId, Runnable todo);
    AsyncTask getTask(String taskId);

    /**
     * Gracefully shutdowns TaskManager.
     * Tasks already in queue will be executed an active tasks continue to execute.
     * But new tasks aren't submitted.
     */
    void close() throws Exception;
}
