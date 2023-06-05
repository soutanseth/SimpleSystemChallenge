package com.simplesystem.challenge.simpletodo.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simplesystem.challenge.simpletodo.dto.AddTodoItemDto;
import com.simplesystem.challenge.simpletodo.dto.TodoDto;
import com.simplesystem.challenge.simpletodo.dto.UpdateTodoDescriptionDto;
import com.simplesystem.challenge.simpletodo.exception.TodoException;
import com.simplesystem.challenge.simpletodo.services.TodoService;
import com.simplesystem.challenge.simpletodo.validation.util.ValidationUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/todo")
public class TodoController {
	
	private final Logger LOGGER = LogManager.getLogger(getClass());
	
	private TodoService todoService;
	
	public TodoController(TodoService todoService) {
		this.todoService=todoService;
	}
	
	@PostMapping
	public TodoDto addTodoItem(@RequestBody @Valid AddTodoItemDto todoDto) {
		LOGGER.debug("inside addTodoItem(): Request body: "+todoDto);
		return todoService.addTodoItem(todoDto);
	}
	
	@GetMapping
	public List<TodoDto> getNotDoneTodoItems() {
		return todoService.getTodoItems(false);
	}
	
	@GetMapping("all")
	public List<TodoDto> getAllTodoItems() {
		return todoService.getTodoItems(true);
	}
	
	@PutMapping("{id}/update")
	public TodoDto updateTodoItem(@PathVariable String id, @RequestBody @Valid UpdateTodoDescriptionDto updateTodoDescriptionDto) throws TodoException {
		LOGGER.debug("inside addTodoItem(): id: "+id+", Request body: "+updateTodoDescriptionDto);
		TodoDto todoDto = new TodoDto();
		todoDto.setId(id);
		todoDto.setDescription(updateTodoDescriptionDto.getDescription());
		return todoService.updateTodoItem(todoDto);
	}
	
	@GetMapping("{id}")
	public TodoDto getDetails(@PathVariable String id) throws TodoException {
		LOGGER.debug("inside addTodoItem(): id: "+id);
		return todoService.getDetails(id);
	}
	
	@PutMapping("{id}/mark/{status}")
	public TodoDto updateTodoItemStatus(@PathVariable String id, @PathVariable String status) throws TodoException {
		LOGGER.debug("inside addTodoItem(): id: "+id+", Status: "+status);
		TodoDto todoDto = new TodoDto();
		todoDto.setId(id);
		todoDto.setStatus(ValidationUtil.validateStatusStrAndGet(status));
		return todoService.updateTodoItem(todoDto);
	}
	


}
