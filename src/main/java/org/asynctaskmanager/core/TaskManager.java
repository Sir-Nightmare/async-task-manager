package org.asynctaskmanager.core;


public interface TaskManager extends AutoCloseable {
    AsyncTask submit(Runnable todo);
    AsyncTask submit(String taskId, Runnable todo);
    void close() throws Exception;
}
