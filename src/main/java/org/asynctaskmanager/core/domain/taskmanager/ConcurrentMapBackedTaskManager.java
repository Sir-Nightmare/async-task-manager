package org.asynctaskmanager.core.domain.taskmanager;

import net.jcip.annotations.ThreadSafe;
import org.asynctaskmanager.core.domain.exception.TaskAlreadySubmittedException;
import org.asynctaskmanager.core.domain.exception.TaskNotFoundException;
import org.asynctaskmanager.core.domain.task.AsyncTask;
import org.asynctaskmanager.core.domain.task.FutureBackedAsyncTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

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
    public AsyncTask submit(Callable<?> todo) throws TaskAlreadySubmittedException {
        FutureBackedAsyncTask task = new FutureBackedAsyncTask(executor.submit(todo));
        if (tasks.containsKey(task.getId())) throw new TaskAlreadySubmittedException(task.getId());

        tasks.put(task.getId(), task);
        logger.debug("Submitted task {}", task.getId());
        return task;
    }

    @Override
    public AsyncTask submit(String taskId, Callable<?> todo) throws TaskAlreadySubmittedException {
        FutureBackedAsyncTask task = new FutureBackedAsyncTask(taskId, executor.submit(todo));
        if (tasks.containsKey(task.getId())) throw new TaskAlreadySubmittedException(task.getId());

        tasks.put(task.getId(), task);
        logger.debug("Submitted task {}", task.getId());
        return task;
    }


    @Override
    public AsyncTask getTask(String taskId) throws TaskNotFoundException {
        if (!tasks.containsKey(taskId)) throw new TaskNotFoundException(taskId);

        return tasks.get(taskId);
    }

    @Override
    public Collection<AsyncTask> getTasks() {
        return tasks.values();
    }


    @Override
    public void deleteCompleted() {
        tasks.entrySet().stream()
            .filter(pair -> pair.getValue().isDone())
            .map(pair -> pair.getKey())
            .forEach(key -> tasks.remove(key));
        ;
    }

    @Override
    public void close() throws Exception {
        executor.shutdown();
    }

    @Override
    public Collection<AsyncTask> forceClose() {
        executor.shutdownNow();

        return tasks.values().stream()
                .filter(task -> !task.isDone())
                .collect(Collectors.toList());
    }
    //endregion
}
