package org.asynctaskmanager.core;


public interface TaskManager extends AutoCloseable {
    Task submit(Runnable todo);
    Task submit(String taskId, Runnable todo);
    void close() throws Exception;
}
