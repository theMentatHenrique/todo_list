package br.com.henriquepaim.todolist.Task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public Taskmodel create(@RequestBody Taskmodel taskmodel) {
        this.taskRepository.save(taskmodel);
        return taskmodel;

    }
}
