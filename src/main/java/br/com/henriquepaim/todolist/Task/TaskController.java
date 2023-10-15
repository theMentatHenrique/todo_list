package br.com.henriquepaim.todolist.Task;

import br.com.henriquepaim.todolist.Util.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody Taskmodel taskmodel, HttpServletRequest request) {
        taskmodel.setIdUser((UUID) request.getAttribute("userId"));
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(taskmodel.getStartAT()) || now.isAfter(taskmodel.getEndAT())) {
            return ResponseEntity.status(400)
                    .body("A data de início/término deve ser maior que a data atual.");

        }

        if (taskmodel.getStartAT().isAfter(taskmodel.getEndAT())) {
            return ResponseEntity.status(400)
                    .body("A data de início deve ser menor que a data de término.");
        }
        this.taskRepository.save(taskmodel);
        return ResponseEntity.status(200).body(taskmodel);

    }

    @GetMapping("/")
    public List<Taskmodel> list(HttpServletRequest request) {
        Object userId = request.getAttribute("userId");
        List<Taskmodel> byUserId = this.taskRepository.findByIdUser((UUID) userId);
        return byUserId;
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody Taskmodel taskmodel, HttpServletRequest httpServletRequest, @PathVariable UUID id) {
        Taskmodel task = taskRepository.findById(id).orElse(null);
        if (task == null) {
            return ResponseEntity.status(400).body("Tarefa não encontrada.");
        }
        Object userId = httpServletRequest.getAttribute("userId");

        if (task.getIdUser().equals(userId)) {
            return ResponseEntity.status(400).body("Usuário não tem permissão para alterar dados desta tarefa.");

        }

        Utils.copyNotNullProperties(taskmodel, task);
        Taskmodel save = this.taskRepository.save(taskmodel);
        return ResponseEntity.status(200).body(save);

    }
}
