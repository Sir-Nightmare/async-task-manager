package org.asynctaskmanager.core.domain.taskmanager;


import org.asynctaskmanager.core.domain.exception.TaskAlreadySubmittedException;
import org.asynctaskmanager.core.domain.exception.TaskNotFoundException;
import org.asynctaskmanager.core.domain.task.AsyncTask;

import java.util.Collection;

public interface TaskManager extends AutoCloseable {
    AsyncTask submit(Runnable todo) throws TaskAlreadySubmittedException;
    AsyncTask submit(String taskId, Runnable todo) throws TaskAlreadySubmittedException;

    AsyncTask getTask(String taskId) throws TaskNotFoundException;
    Collection<AsyncTask> getTasks();

    void deleteCompleted();

    /**
     * Gracefully shutdowns TaskManager.
     * Tasks already in queue will be executed and active tasks continue to execute.
     * But new tasks aren't submitted.
     */
    void close() throws Exception;
}
