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
@RequestMapping("/tasks")
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
POST http://localhost:8080/api/v1/tasks
Content-Type: application/json

{ 
   "description" : "testDescription" 
}
```

Example response:
```
{"id":"1711014896","done":false}
```

## Submit Task with ID
```
POST http://localhost:8080/api/v1/tasks/{task-id}
Content-Type: application/json

{ 
   "description" : "testDescription" 
}
```

Example response:
```
{"id":"1711014896","done":false}
```


## Get Task and Status
```
GET http://localhost:8080/api/v1/tasks/{task-id}
```

Example Response:
```
{"id":"811194812","done":true}
```

## Get All Tasks
```
GET http://localhost:8080/api/v1/tasks
```

Example Response:
```
[{"id":"1765246487","done":true},{"id":"248885500","done":false},{"id":"1234","done":true},{"id":"1500234172","done":true},{"id":"1880537132","done":false}]
```

## Delete Completed Tasks
```
DELETE http://localhost:8080/api/v1/tasks/completed
```