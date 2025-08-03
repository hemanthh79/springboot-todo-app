package com.TodoListWeb.TodoList.repository;

import com.TodoListWeb.TodoList.model.Task;
import com.TodoListWeb.TodoList.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByUserAndDueDate(User user, LocalDate dueDate);

    List<Task> findByUserAndDueDateAndCompleted(User user, LocalDate dueDate, boolean completed);

    List<Task> findByUser(User user);

    List<Task> findByDueDateBetween(LocalDate dateAdded, LocalDate dueDate);

    Task findByUserAndId(User user, Integer id);

    Task getById(Integer id);

    int countByCompleted(boolean completed);
}
