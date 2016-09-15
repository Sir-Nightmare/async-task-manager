package org.asynctaskmanager.example;

import org.asynctaskmanager.core.controller.BaseTaskController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Callable;


@RestController
@RequestMapping("/tasks")
public class SimpleTaskController extends BaseTaskController<SimpleTaskRequest, Void> {
    private static final Logger logger = LoggerFactory.getLogger(BaseTaskController.class);

    @Override
    protected Class<SimpleTaskRequest> getTaskRequestType() {
        return SimpleTaskRequest.class;
    }

    @Override
    protected Callable<Void> createTaskFromRequest(SimpleTaskRequest simpleTaskRequest, HttpServletRequest httpRequest) {
        return () -> {
            logger.debug("Starting task with description {}", simpleTaskRequest.getDescription());
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.debug("Done task with description {}", simpleTaskRequest.getDescription());
            return null;
        };
    }
}
