package com.example.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class TodoController {

    @Autowired
    private TodoRepository repository;

    // 1. Home Page (Supports Search)
    @GetMapping("/")
    public String index(Model model, @RequestParam(required = false) String keyword) {
        List<Todo> tasks;
        if (keyword != null && !keyword.isEmpty()) {
            tasks = repository.findByTaskContainingIgnoreCase(keyword); // Search results
        } else {
            tasks = repository.findAll(); // Show all if no search
        }
        model.addAttribute("todos", tasks);
        model.addAttribute("keyword", keyword); // Keep the search term in the box
        return "index";
    }

    // 2. Add New Task
    @PostMapping("/add")
    public String addTodo(@RequestParam String task) {
        Todo todo = new Todo();
        todo.setTask(task);
        repository.save(todo);
        return "redirect:/";
    }

    // 3. Delete Task
    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/";
    }

    // 4. Show Edit Form (New Feature)
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Todo todo = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Id:" + id));
        model.addAttribute("todo", todo);
        return "edit";
    }

    // 5. Save Updates (New Feature)
    @PostMapping("/update/{id}")
    public String updateTodo(@PathVariable Long id, @ModelAttribute("todo") Todo todo) {
        // Ensure the ID matches
        todo.setId(id); 
        repository.save(todo);
        return "redirect:/";
    }
}