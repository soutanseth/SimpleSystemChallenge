package com.simplesystem.challenge.simpletodo.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simplesystem.challenge.simpletodo.models.Todo;
import com.simplesystem.challenge.simpletodo.models.TodoStatus;

@Repository
public interface TodoRepository extends JpaRepository<Todo, String>{
	
	List<Todo> findByStatus(TodoStatus status);

}
