package br.com.henriquepaim.todolist.Task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ITaskRepository extends JpaRepository<Taskmodel, UUID> {
    List<Taskmodel> findByUserId(UUID userId);
}
