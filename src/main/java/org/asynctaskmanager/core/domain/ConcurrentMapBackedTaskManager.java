package org.asynctaskmanager.core.domain;

import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Memory-backed _not persisted_ task manager.
 */
@ThreadSafe
@Component
public class ConcurrentMapBackedTaskManager implements TaskManager {
    private static final Logger logger = LoggerFactory.getLogger(ConcurrentMapBackedTaskManager.class);

    //region State
    private final Map<String, AsyncTask> tasks = new ConcurrentHashMap<>();
    private final ExecutorService executor;

    @Autowired
    public ConcurrentMapBackedTaskManager(ExecutorService executor) {
        if (executor == null) throw new IllegalArgumentException("No executor given for task manager");
        this.executor = executor;
    }
    //endregion

    //region Public Api
    @Override
    public AsyncTask submit(Runnable todo) {
        FutureBackedAsyncTask task = new FutureBackedAsyncTask(executor.submit(todo));
        tasks.put(task.getId(), task);
        logger.debug("Submitted task {}", task.getId());
        return task;
    }

    @Override
    public AsyncTask submit(String taskId, Runnable todo) {
        FutureBackedAsyncTask task = new FutureBackedAsyncTask(taskId, executor.submit(todo));
        tasks.put(task.getId(), task);
        logger.debug("Submitted task {}", task.getId());
        return task;
    }


    @Override
    public AsyncTask getTask(String taskId) {
        return tasks.get(taskId);
    }

    @Override
    public Collection<AsyncTask> getTasks() {
        return tasks.values();
    }

    @Override
    public void close() throws Exception {
        executor.shutdown();
    }
    //endregion
}
