package com.simplesystem.challenge.simpletodo.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.simplesystem.challenge.simpletodo.dao.TodoRepository;
import com.simplesystem.challenge.simpletodo.dto.AddTodoItemDto;
import com.simplesystem.challenge.simpletodo.dto.TodoDto;
import com.simplesystem.challenge.simpletodo.exception.TodoException;
import com.simplesystem.challenge.simpletodo.models.Todo;
import com.simplesystem.challenge.simpletodo.models.TodoStatus;

@Service
public class TodoService {
	
	private final Logger LOGGER= LogManager.getLogger(getClass());

	private TodoRepository todoRepository;
	
	private ModelMapper modelMapper;
	
	public TodoService(TodoRepository todoRepository, ModelMapper modelMapper) {
		this.todoRepository=todoRepository;
		this.modelMapper=modelMapper;
	}
	
	public TodoDto addTodoItem(AddTodoItemDto todoDto) {
		Todo todoItem=modelMapper.map(todoDto, Todo.class);
		todoItem.setCreatedDateTime(LocalDateTime.now());
		todoItem.setStatus(TodoStatus.NOT_DONE);
		Todo todoObj = todoRepository.save(todoItem);
		return modelMapper.map(todoObj, TodoDto.class);
	}
	
	public List<TodoDto> getTodoItems(boolean includeAll){
		List<Todo> todoItems =null;
		if(includeAll) {
			todoItems = todoRepository.findAll();
		}else {
			todoItems=todoRepository.findByStatus(TodoStatus.NOT_DONE);
		}
		return todoItems.stream().map((todoItem) -> modelMapper.map(todoItem, TodoDto.class)).collect(Collectors.toList());
	}
	
	public TodoDto updateTodoItem(TodoDto todoDto) throws TodoException {
		LOGGER.debug("TodoDto object: "+todoDto);
		Optional<Todo> exisitngTodoOptional = todoRepository.findById(todoDto.getId());
		if(exisitngTodoOptional.isPresent()) {
			Todo todoObj=exisitngTodoOptional.get();
			if(todoObj.getStatus() == TodoStatus.PAST_DUE) {
				throw new TodoException("Item with id: "+todoDto.getId()+" is Past Due, can not be udpated!");
			}
			if(todoDto.getDescription()!=null && !todoDto.getDescription().isBlank()) {
				todoObj.setDescription(todoDto.getDescription());
			}
			if(todoDto.getStatus()!=null) {
				todoObj.setStatus(todoDto.getStatus());
				switch(todoDto.getStatus()) {
					case DONE:
						todoObj.setCompletedDateTime(LocalDateTime.now());
						break;
					case NOT_DONE:
						todoObj.setCompletedDateTime(null);
						break;
					case PAST_DUE:
				}
			}
			todoObj = todoRepository.save(todoObj);
			return modelMapper.map(todoObj, TodoDto.class);
		}else {
			throw new TodoException("Item with id: "+todoDto.getId()+" not found");
		}
	}
	
	public TodoDto getDetails(String todoId) throws TodoException {
		Optional<Todo> todoOptional=todoRepository.findById(todoId);
		Todo todoObj=todoOptional.orElseThrow(() -> new TodoException("Item with id: "+todoId+" not found"));
		return modelMapper.map(todoObj, TodoDto.class);
	}
	
	@Scheduled(fixedRateString = "${fixedRate.in.milliseconds}")
	public void updateNotDoneStatus() {
		LOGGER.debug("running updateNotDoneStatus() - start");
		List<Todo> notDoneTodoItemList = todoRepository.findByStatus(TodoStatus.NOT_DONE);
		final LocalDateTime current = LocalDateTime.now();
		List<Todo> toBeUpdatedList = notDoneTodoItemList.parallelStream().filter((notDoneTodoItem) -> notDoneTodoItem.getDueDateTime().isBefore(current)).collect(Collectors.toList());
		toBeUpdatedList.stream().forEach((e) -> e.setStatus(TodoStatus.PAST_DUE));
		todoRepository.saveAll(toBeUpdatedList);
	}
	
}
