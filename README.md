# Simple REST API for long-running tasks
The library purpose is to get simple but production-ready solution for managing long-running tasks.
Just plug and make REST calls.

# How To Develop
Just add two classes based on framework ones: your TaskController and your TaskRequest.
```
public class SimpleTaskRequest {
	//Your TaskRequest content
}

@RestController
@RequestMapping("/task")
public class SimpleTaskController extends BaseTaskController<SimpleTaskRequest> {
    @Override
    protected Class<SimpleTaskRequest> getTaskRequestType() {
        return SimpleTaskRequest.class;
    }

    @Override
    protected Runnable createTaskFromRequest(SimpleTaskRequest simpleTaskRequest) {
        return () -> {
        	//map your TaskRequest to Runnable
        };
    }
}
```

# Use Cases
## Submit Task
```
POST http://localhost:8080/api/v1/task
Content-Type: application/json

{ 
   "description" : "testDescription" 
}
```

