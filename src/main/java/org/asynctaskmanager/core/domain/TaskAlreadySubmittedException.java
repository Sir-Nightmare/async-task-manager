package org.asynctaskmanager.core.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Task with same ID is already submitted")
public class TaskAlreadySubmittedException extends Exception {
    public TaskAlreadySubmittedException(String taskId) {
        super(taskId);
    }
}
