package org.asynctaskmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.asynctaskmanager.core.TaskManager;
import org.asynctaskmanager.core.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Base implementation of TaskController.
 * Code reuse achieved by extending it and setting up generic types with overriding _getRqClass_ and _createTask_. See [GoF] Template method.
 * @param <R> TaskRequest type.
 */
public abstract class BaseTaskController<R> {
    @Autowired private ObjectMapper jsonMapper;
    @Autowired private TaskManager taskManager;

    //region Steps to implement in subclasses
    /**
     * Custom mapping of request to executable logic made in subclasses. [GoF] Template method.
     */
    protected abstract Runnable createTaskFromRequest(R taskRequest);

    /**
     * Need to get _concrete_ type of request structure for right json mapping from raw request string. See [GoF] Template method.
     */
    protected abstract Class<R> getTaskRequestType();
    //endregion

    //region public API
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody Task submit(@RequestBody String rawTaskRequest) throws IOException { //FIXME exceptions
        return submit(null, rawTaskRequest);
    }

    @RequestMapping(value = "/{taskId}", method = RequestMethod.POST)
    public @ResponseBody Task submit(@PathVariable("taskId") String taskId, @RequestBody String rawTaskRequest) throws IOException { //FIXME exceptions
        if (isEmpty(rawTaskRequest)) throw new IllegalArgumentException("Empty task request");

        R taskRequest = jsonMapper.readValue(rawTaskRequest, this.getTaskRequestType());

        return taskId == null ?
            taskManager.submit(this.createTaskFromRequest(taskRequest)) :
            taskManager.submit(taskId, this.createTaskFromRequest(taskRequest));
    }

    @RequestMapping(value = "/{taskId}", method = RequestMethod.GET)
    public @ResponseBody String getTask(@PathVariable("taskId") String taskId) {
        throw new RuntimeException("Not supported");
    }
    //endregion
}
