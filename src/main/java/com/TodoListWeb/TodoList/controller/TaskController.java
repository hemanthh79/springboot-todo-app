package com.TodoListWeb.TodoList.controller;

import com.TodoListWeb.TodoList.model.Task;
import com.TodoListWeb.TodoList.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        List<Task> todayTasks = taskService.getTodayTasksForCurrentUser();

        model.addAttribute("todayTasks", todayTasks);

        List<Task> allTasks = taskService.getAllTasksForCurrentUser();
        sortTasksByDueDate(allTasks);

        model.addAttribute("allTasks", allTasks);

        LocalDateTime localDateTime = LocalDateTime.now();
        String formattedDate = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(localDateTime);
        model.addAttribute("serverTime", LocalDateTime.now());

        model.addAttribute("completedCount", taskService.countByCompleted(true));
        model.addAttribute("pendingCount", taskService.countByCompleted(false));
        return "dashboard";
    }

    public void sortTasksByDueDate(List<Task> tasks) {
        Collections.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                if(o1.getDueDate() == null && o2.getDueDate() == null) return 0;
                if(o1.getDueDate() == null) return 1;
                if(o2.getDueDate() == null) return -1;

                return o1.getDueDate().compareTo(o2.getDueDate());

            }
        });
    }

    @GetMapping("/addtask")
    public String addTask(Model model) {
        model.addAttribute("task", new Task());
        return "addtask";
    }

    @PostMapping("/addtask")
    public String saveTask(@ModelAttribute("task") Task task, Model model, RedirectAttributes redirectAttributes) {
        try {
            taskService.saveTask(task);

            redirectAttributes.addFlashAttribute("successMessage", "task added successfully!");
            return "redirect:/dashboard";
        }

        catch (Exception e) {
            model.addAttribute("task", model);
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to add task! Please try again");
            return "redirect:/addtask";
        }
    }

    @PostMapping("/task/markDone")
    public String markTaskAsDone(@RequestParam("taskId") Integer taskId, RedirectAttributes redirectAttributes) {
        boolean isMarkedDone = taskService.markTaskAsDone(taskId);

        if(isMarkedDone) {
            redirectAttributes.addFlashAttribute("successMessage", "Task marked as Done");
        }
        else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to mark task as Done! Please try again");
        }
        return "redirect:/dashboard";
    }


    @PostMapping("/task/viewtask")
    public String viewTask(@RequestParam("taskId") Integer taskId, Model model,  RedirectAttributes redirectAttributes) {

        if(taskId == null ){
            redirectAttributes.addFlashAttribute("errorMessage","Invalid attempt to view task.");
            return "redirect:/dashboard";
        }

        Task task = taskService.getTaskByIdAny(taskId);


        if (task != null) {
            model.addAttribute("task", task);
        } else {
            model.addAttribute("error", "No task to display.");
        }

        return "viewtask";
    }

    @PostMapping("/task/edittask")
    public String editTaskForm(@RequestParam("taskId") Integer taskId, Model model) {

        Task task = taskService.getTaskById(taskId);

        if (task != null) {
            model.addAttribute("task", task);
        } else {
            model.addAttribute("error", "No task to display.");
        }

        return "edittask";
    }


    @PostMapping("/updatetask")
    public String updateTask(@ModelAttribute("task") Task task, RedirectAttributes redirectAttributes) {
        try {
            taskService.updateTaskForUser(task);
            redirectAttributes.addFlashAttribute("successMessage", "Task updated successfully!");
            return "redirect:/dashboard";
        } catch (Exception e) {
            System.out.println("Error : " + e);
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update task. Please try again.");
            return "redirect:/edittask";
        }
    }


    @PostMapping("/task/delete")
    public String deleteTask(@RequestParam("taskId") Integer taskId, RedirectAttributes redirectAttributes) {
        Task task = taskService.getTaskByIdAny(taskId);

        if (task != null) {
            if (taskService.deleteTask(task)) {
                redirectAttributes.addFlashAttribute("successMessage", "Task deleted successfully.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Task cannot be deleted by user.");
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Task not found.");
        }

        return "redirect:/dashboard";
    }


}
