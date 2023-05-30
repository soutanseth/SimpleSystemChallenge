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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simplesystem.challenge.simpletodo.dto.AddTodoItemDto;
import com.simplesystem.challenge.simpletodo.dto.TodoDto;
import com.simplesystem.challenge.simpletodo.dto.UpdateTodoStatusDto;
import com.simplesystem.challenge.simpletodo.exception.TodoException;
import com.simplesystem.challenge.simpletodo.services.TodoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/todo")
public class TodoController {
	
//	private final Logger LOGGER = LogManager.getLogger(getClass());
	
	private TodoService todoService;
	
	public TodoController(TodoService todoService) {
		this.todoService=todoService;
	}
	
	@PostMapping //TODO: Use Single Dto with Validation
	public TodoDto addTodoItem(@RequestBody @Valid AddTodoItemDto todoDto) {
		return todoService.addTodoItem(todoDto);
	}
	
	@GetMapping
	public List<TodoDto> getTodoItems(@RequestParam(required = false)  String filter) {
		return todoService.getTodoItems(filter);
	}
	
	@PutMapping("{id}/update") //TODO: Use Single Dto with Validation
	public TodoDto updateTodoItem(@PathVariable String id, @RequestBody @Valid TodoDto updateTodoDescriptionDto) throws TodoException {
		TodoDto todoDto = new TodoDto();
		todoDto.setId(id);
		todoDto.setDescription(updateTodoDescriptionDto.getDescription());
		return todoService.updateTodoItem(todoDto);
	}
	
	@GetMapping("{id}")
	public TodoDto getDetails(@PathVariable String todoId) throws TodoException {
		return todoService.getDetails(todoId);
	}
	
	@PutMapping("{id}/mark") //TODO: Use Single Dto with Validation
	public TodoDto updateTodoItemStatus(@PathVariable String id, @RequestBody @Valid UpdateTodoStatusDto status) throws TodoException {
		TodoDto todoDto = new TodoDto();
		todoDto.setId(id);
		todoDto.setStatus(status.getStatus());
		return todoService.updateTodoItem(todoDto);
	}

}
