package org.asynctaskmanager.example;

import org.asynctaskmanager.core.controller.BaseTaskController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/task")
public class SimpleTaskController extends BaseTaskController<SimpleTaskRequest> {
    private static final Logger logger = LoggerFactory.getLogger(BaseTaskController.class);

    @Override
    protected Class<SimpleTaskRequest> getTaskRequestType() {
        return SimpleTaskRequest.class;
    }

    @Override
    protected Runnable createTaskFromRequest(SimpleTaskRequest simpleTaskRequest) {
        return () -> {
            logger.debug("Starting task with description {}", simpleTaskRequest.getDescription());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.debug("Done task with description {}", simpleTaskRequest.getDescription());
        };
    }
}
