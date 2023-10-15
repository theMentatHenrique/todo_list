package br.com.henriquepaim.todolist.Task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ITaskRepository extends JpaRepository<Taskmodel, UUID> {
    List<Taskmodel> findByIdUser(UUID idUser);
}
