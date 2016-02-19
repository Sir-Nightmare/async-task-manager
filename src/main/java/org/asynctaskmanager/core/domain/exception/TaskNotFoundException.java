package org.asynctaskmanager.core.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Task with this ID not found. It may be intentionally cleared or lost after server restart.")
public class TaskNotFoundException extends Exception {
    public TaskNotFoundException(String taskId) {
        super(taskId);
    }
}
