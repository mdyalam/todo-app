package com.example.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    // Custom query to search for tasks (Case Insensitive)
    List<Todo> findByTaskContainingIgnoreCase(String keyword);
}