package com.simplesystem.challenge.simpletodo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.simplesystem.challenge.simpletodo.config.DateFormatterConfig;
import com.simplesystem.challenge.simpletodo.config.ModelMapperConfig;
import com.simplesystem.challenge.simpletodo.dao.TodoRepository;
import com.simplesystem.challenge.simpletodo.dto.AddTodoItemDto;
import com.simplesystem.challenge.simpletodo.dto.TodoDto;
import com.simplesystem.challenge.simpletodo.exception.TodoException;
import com.simplesystem.challenge.simpletodo.models.Todo;
import com.simplesystem.challenge.simpletodo.models.TodoStatus;
import com.simplesystem.challenge.simpletodo.services.TodoService;

@ExtendWith(SpringExtension.class)
@Import({ModelMapperConfig.class, DateFormatterConfig.class})
public class TodoServiceTest {

	@InjectMocks
	TodoService todoService;
	
	@Mock
	TodoRepository todoRepository;
	
	@Mock
	ModelMapper modelMapper;
	
	Todo todo;
	
	TodoDto todoDto;
	

	@BeforeEach
	public void init() {
		todo=new Todo();
		todo.setDescription("Todo-1");
		todo.setDueDateTime(LocalDateTime.parse("2023-06-06 03:14", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
		todo.setStatus(TodoStatus.NOT_DONE);
		todo.setCreatedDateTime(LocalDateTime.parse("2023-05-31 03:14", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
		todo.setId("1");
		
		todoDto=new TodoDto();
		todoDto.setDescription("Todo-1");
		todoDto.setDueDateTime(LocalDateTime.parse("2023-06-06 03:14", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
		todoDto.setStatus(TodoStatus.NOT_DONE);
		todoDto.setCreatedDateTime(LocalDateTime.parse("2023-05-31 03:14", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
		todoDto.setId("1");
		
	}
	
	@Test
	public void testAddTodoItem() {
		
		AddTodoItemDto addTodoItemDto=new AddTodoItemDto();
		addTodoItemDto.setDescription("Todo-1");
		addTodoItemDto.setDueDateTime(LocalDateTime.parse("2023-06-06 03:14", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
		
		when(modelMapper.map(addTodoItemDto, Todo.class)).thenReturn(todo);
		when(modelMapper.map(any(), eq(TodoDto.class))).thenReturn(todoDto);
		
		TodoDto result= todoService.addTodoItem(addTodoItemDto);
		assertNotNull(result.getCreatedDateTime());
		assertEquals(result.getStatus(), TodoStatus.NOT_DONE);
		assertEquals(result.getDescription(), "Todo-1");
		assertEquals(result.getDueDateTime(), LocalDateTime.parse("2023-06-06 03:14", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

	}
	
	@Test
	public void testGetnotDoneTodoItems(){
		when(todoRepository.findByStatus(TodoStatus.NOT_DONE)).thenReturn(Arrays.asList(todo));
		when(modelMapper.map(todo, TodoDto.class)).thenReturn(todoDto);
		List<TodoDto> result = todoService.getTodoItems(false);
		assertNotNull(result);
		assertNotNull(result.get(0).getCreatedDateTime());
		assertEquals(result.get(0).getStatus(), TodoStatus.NOT_DONE);
		assertEquals(result.get(0).getDescription(), "Todo-1");
		assertEquals(result.get(0).getDueDateTime(), LocalDateTime.parse("2023-06-06 03:14", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
		
		verify(todoRepository, times(1)).findByStatus(TodoStatus.NOT_DONE);
		
	}
	
	@Test
	public void testGetAllTodoItems(){
		when(todoRepository.findAll()).thenReturn(Arrays.asList(todo));
		when(modelMapper.map(todo, TodoDto.class)).thenReturn(todoDto);
		List<TodoDto> result = todoService.getTodoItems(true);
		assertNotNull(result);
		assertNotNull(result.get(0).getCreatedDateTime());
		assertEquals(result.get(0).getStatus(), TodoStatus.NOT_DONE);
		assertEquals(result.get(0).getDescription(), "Todo-1");
		assertEquals(result.get(0).getDueDateTime(), LocalDateTime.parse("2023-06-06 03:14", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
		
		verify(todoRepository, times(1)).findAll();

	}
	
	@Test
	public void testGetDetails() {
		when(todoRepository.findById("1")).thenReturn(Optional.of(todo));
		when(modelMapper.map(todo, TodoDto.class)).thenReturn(todoDto);
		try {
			TodoDto result=todoService.getDetails("1");
			assertNotNull(result.getCreatedDateTime());
			assertEquals(result.getDescription(), "Todo-1");
			assertEquals(result.getDueDateTime(), LocalDateTime.parse("2023-06-06 03:14", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
			verify(todoRepository, times(1)).findById("1");
		} catch (TodoException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testUpdateTodoItemDescription() {
		when(todoRepository.findById("1")).thenReturn(Optional.of(todo));
		when(modelMapper.map(any(), eq(TodoDto.class))).thenReturn(todoDto);
		todoDto.setDescription("Todo-1(Updated)");
		try {
			TodoDto result=todoService.updateTodoItem(todoDto);
			assertNotNull(result);
			assertEquals(result.getDescription(), "Todo-1(Updated)");
			verify(todoRepository, times(1)).findById("1");
		} catch (TodoException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testUpdateTodoItemStatus_Done() {
		when(todoRepository.findById("1")).thenReturn(Optional.of(todo));
		todoDto.setCompletedDateTime(LocalDateTime.now());
		when(modelMapper.map(any(), eq(TodoDto.class))).thenReturn(todoDto);
		todoDto.setStatus(TodoStatus.DONE);
		try {
			TodoDto result=todoService.updateTodoItem(todoDto);
			assertNotNull(result);
			assertEquals(result.getDescription(), "Todo-1");
			assertEquals(result.getStatus(), TodoStatus.DONE);
			assertNotNull(result.getCompletedDateTime());
			verify(todoRepository, times(1)).findById("1");
		} catch (TodoException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testUpdateTodoItemStatus_NotDone() {
		when(todoRepository.findById("1")).thenReturn(Optional.of(todo));
		when(modelMapper.map(any(), eq(TodoDto.class))).thenReturn(todoDto);
		todoDto.setStatus(TodoStatus.NOT_DONE);
		try {
			TodoDto result=todoService.updateTodoItem(todoDto);
			assertNotNull(result);
			assertEquals(result.getDescription(), "Todo-1");
			assertEquals(result.getStatus(), TodoStatus.NOT_DONE);
			assertNull(result.getCompletedDateTime());
			verify(todoRepository, times(1)).findById("1");
		} catch (TodoException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testUpdateTodoItem() {
		when(todoRepository.findByStatus(TodoStatus.NOT_DONE)).thenReturn(Arrays.asList(todo));
		when(modelMapper.map(any(), eq(TodoDto.class))).thenReturn(todoDto);
		todoService.updateNotDoneStatus();
		verify(todoRepository, times(1)).findByStatus(TodoStatus.NOT_DONE);
		verify(todoRepository, times(1)).saveAll(any());

		
		
	}
}
