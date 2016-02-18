package org.asynctaskmanager.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Component
public class ConcurrentCollectionBackedTaskManager implements TaskManager {
    private static final Logger logger = LoggerFactory.getLogger(ConcurrentCollectionBackedTaskManager.class);

    //region State
    private ExecutorService executor;

    @Autowired
    public ConcurrentCollectionBackedTaskManager(ExecutorService executor) {
        if (executor == null) throw new IllegalArgumentException("No executor given for task manager");
        this.executor = executor;
    }
    //endregion

    //region Public Api
    @Override
    public Task submit(Runnable todo) {
        FutureBackedTask task = new FutureBackedTask(executor.submit(todo));
        logger.debug("Submitted task {}", task.getId());
        return task;
    }

    @Override
    public Task submit(String taskId, Runnable todo) {
        FutureBackedTask task = new FutureBackedTask(taskId, executor.submit(todo));
        logger.debug("Submitted task {}", task.getId());
        return task;
    }

    /**
     * Gracefully shutdowns TaskManager.
     * Active tasks continue to execute but new tasks aren't submitted.
     */
    @Override
    public void close() throws Exception {
        executor.shutdown();
    }
    //endregion
}
