# Simple REST API for long-running tasks
The library purpose is to get simple but production-ready solution for managing long-running tasks.
Just plug and make REST calls.

# Use Cases
## Submit Task
```
POST http://localhost:8080/api/v1/task
Content-Type: application/json

{ 
   "description" : "testDescription" 
}
```

