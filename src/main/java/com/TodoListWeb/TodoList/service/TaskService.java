package com.TodoListWeb.TodoList.service;

import com.TodoListWeb.TodoList.model.Task;
import com.TodoListWeb.TodoList.model.User;
import com.TodoListWeb.TodoList.repository.TaskRepository;
import com.TodoListWeb.TodoList.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public Task saveTask(Task task) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = userRepository.findByUsername(username);

        task.setUser(user);

        task.setDateAdded(LocalDate.now());

        return taskRepository.save(task);
    }

    public List<Task> getTodayTasksForCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication.getName());

        LocalDate currentDate = LocalDate.now();

        List<Task> taskListForToday = new ArrayList<>();

        taskListForToday = taskRepository.findByUserAndDueDateAndCompleted(user, currentDate, false);

        return taskListForToday;
    }

    public List<Task> getAllTasksForCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication.getName());

        return taskRepository.findByUser(user);
    }

    public boolean markTaskAsDone(Integer taskId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = userRepository.findByUsername(username);

        Task task = taskRepository.findByUserAndId(user, taskId);

        if(task != null && !task.isCompleted()) {
            task.setCompleted(true);

            task.setCompletionDate(LocalDate.now());
            taskRepository.save(task);
            return true;
        }

        return false;
    }

    public Task getTaskById(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = userRepository.findByUsername(username);

        Task task = taskRepository.findByUserAndId(user, id);

        if(task != null && !task.isCompleted()){
            return task;
        }

        return null;
    }

    public Task getTaskByIdAny(Integer taskId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication.getName());

        Task task = taskRepository.findByUserAndId(user, taskId);
        if(task != null) {
            return task;
        }

        return null;
    }

    public boolean updateTaskForUser(Task task) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = userRepository.findByUsername(username);

        Task taskInDb = taskRepository.getById(task.getId());

        if(user != null && !user.getUsername().equals(taskInDb.getUser().getUsername())) {
            return false;
        }

        Task existingTask = taskRepository.findByUserAndId(user, task.getId());
        if(existingTask != null) {
            existingTask.setTitle(task.getTitle());
            existingTask.setDescription(task.getDescription());
            existingTask.setPriority(task.getPriority());
            existingTask.setDueDate(task.getDueDate());
            existingTask.setType(task.getType());

            existingTask.setDateAdded(LocalDate.now());

            Task taskUpdated = taskRepository.save(existingTask);

            if(taskUpdated != null) return true;
        }
        return false;
    }

    public boolean deleteTask(Task task) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication.getName());

        Task taskInDb = taskRepository.getById(task.getId());

        if(user != null && !user.getUsername().equals(taskInDb.getUser().getUsername())) {
            return false;
        }

        Task existingTask = taskRepository.findByUserAndId(task.getUser(), task.getId());

        if(existingTask != null) {
            taskRepository.delete(existingTask);
            return true;
        }

        return false;
    }

    public int countByCompletedAndUserId(boolean completedStatus) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication.getName());
        return taskRepository.countByCompletedAndUserId(completedStatus, user.getId());
    }
}
