package com.simplesystem.challenge.simpletodo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.simplesystem.challenge.simpletodo.config.DateFormatterConfig;
import com.simplesystem.challenge.simpletodo.config.ModelMapperConfig;
import com.simplesystem.challenge.simpletodo.controllers.TodoController;
import com.simplesystem.challenge.simpletodo.dto.AddTodoItemDto;
import com.simplesystem.challenge.simpletodo.dto.TodoDto;
import com.simplesystem.challenge.simpletodo.models.TodoStatus;
import com.simplesystem.challenge.simpletodo.services.TodoService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TodoController.class)
@Import({ModelMapperConfig.class, DateFormatterConfig.class})
public class TodoControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	TodoService todoService;
	
	TodoDto todoDto;

	@BeforeEach
	public void init() {
		todoDto=new TodoDto();
		todoDto.setDescription("Todo-1");
		todoDto.setDueDateTime(LocalDateTime.parse("2023-06-06 03:14", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
		todoDto.setStatus(TodoStatus.NOT_DONE);
		todoDto.setCreatedDateTime(LocalDateTime.parse("2023-05-31 03:14", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
		todoDto.setId("1");
	}
	
	@Test
	public void testGetAllTodoItems() throws Exception {
		List<TodoDto> retunrAllList= Arrays.asList(todoDto);
		when(todoService.getTodoItems(true)).thenReturn(retunrAllList);
		
		mockMvc.perform(get("/api/todo/all"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", Matchers.hasSize(1)))
		.andExpect(jsonPath("$[0].description", Matchers.is("Todo-1")))
		.andExpect(jsonPath("$[0].dueDateTime", Matchers.is("2023-06-06 03:14")))
		.andExpect(jsonPath("$[0].id", Matchers.is("1")))
		.andExpect(jsonPath("$[0].createdDateTime", Matchers.is("2023-05-31 03:14")))
		.andExpect(jsonPath("$[0].status", Matchers.is(TodoStatus.NOT_DONE.toString())));		
		verify(todoService, times(1)).getTodoItems(true);
	}
	
	@Test
	public void testGetNotDoneTodoItems() throws Exception {
		
		List<TodoDto> retunrAllList= Arrays.asList(todoDto);
		when(todoService.getTodoItems(false)).thenReturn(retunrAllList);
		
		mockMvc.perform(get("/api/todo"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", Matchers.hasSize(1)))
		.andExpect(jsonPath("$[0].description", Matchers.is("Todo-1")))
		.andExpect(jsonPath("$[0].dueDateTime", Matchers.is("2023-06-06 03:14")))
		.andExpect(jsonPath("$[0].id", Matchers.is("1")))
		.andExpect(jsonPath("$[0].createdDateTime", Matchers.is("2023-05-31 03:14")))
		.andExpect(jsonPath("$[0].status", Matchers.is(TodoStatus.NOT_DONE.toString())));		
		verify(todoService, times(1)).getTodoItems(false);

	}
	
	@Test
	public void testAddTodoItem() throws Exception {
		
		when(todoService.addTodoItem(any(AddTodoItemDto.class))).thenReturn(todoDto);
		
		mockMvc.perform(post("/api/todo")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n"
						+ "    \"description\": \"Todo-1\",\r\n"
						+ "    \"dueDateTime\": \"2023-06-06 03:30\"\r\n"
						+ "}")
				)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.description", Matchers.is("Todo-1")))
		.andExpect(jsonPath("$.dueDateTime", Matchers.is("2023-06-06 03:14")))
		.andExpect(jsonPath("$.id", Matchers.is("1")))
		.andExpect(jsonPath("$.createdDateTime", Matchers.is("2023-05-31 03:14")))
		.andExpect(jsonPath("$.status", Matchers.is(TodoStatus.NOT_DONE.toString())));		
		verify(todoService, times(1)).addTodoItem(any(AddTodoItemDto.class));

	}
	
	@Test
	public void testAddTodoItem_mandatoryField() throws Exception {
		
		when(todoService.addTodoItem(any(AddTodoItemDto.class))).thenReturn(todoDto);
		
		mockMvc.perform(post("/api/todo")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n"
						+ "    \"description\": \"Todo-1\""
						+ "}")
				)
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(MockMvcResultMatchers.content().string("validation error"));
		verify(todoService, times(0)).addTodoItem(any(AddTodoItemDto.class));

	}
	
	@Test
	public void testUpdateTodoItem() throws Exception {

		when(todoService.updateTodoItem(any(TodoDto.class))).thenReturn(todoDto);
		
		mockMvc.perform(put("/api/todo/1/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n"
						+ "    \"description\": \"Todo-1(Updated)\"\r\n"
						+ "}")
				)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.description", Matchers.is("Todo-1")))
		.andExpect(jsonPath("$.dueDateTime", Matchers.is("2023-06-06 03:14")))
		.andExpect(jsonPath("$.id", Matchers.is("1")))
		.andExpect(jsonPath("$.createdDateTime", Matchers.is("2023-05-31 03:14")))
		.andExpect(jsonPath("$.status", Matchers.is(TodoStatus.NOT_DONE.toString())));		
		verify(todoService, times(1)).updateTodoItem(any(TodoDto.class));

	}
	
	@Test
	public void testUpdateTodoItem_mandatoryFiled() throws Exception {

		when(todoService.updateTodoItem(any(TodoDto.class))).thenReturn(todoDto);
		
		mockMvc.perform(put("/api/todo/1/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "}")
				)
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(MockMvcResultMatchers.content().string("validation error"));
		
		verify(todoService, times(0)).updateTodoItem(any(TodoDto.class));

	}
	
	@Test
	public void testGetDetails() throws Exception {

		when(todoService.getDetails(anyString())).thenReturn(todoDto);
		
		mockMvc.perform(get("/api/todo/1"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.description", Matchers.is("Todo-1")))
		.andExpect(jsonPath("$.dueDateTime", Matchers.is("2023-06-06 03:14")))
		.andExpect(jsonPath("$.id", Matchers.is("1")))
		.andExpect(jsonPath("$.createdDateTime", Matchers.is("2023-05-31 03:14")))
		.andExpect(jsonPath("$.status", Matchers.is(TodoStatus.NOT_DONE.toString())));		
		verify(todoService, times(1)).getDetails(anyString());

	}
	
	@Test
	public void testupdateTodoItemStatus_markAsDone() throws Exception {

		todoDto.setStatus(TodoStatus.DONE);

		when(todoService.updateTodoItem(any(TodoDto.class))).thenReturn(todoDto);
		
		mockMvc.perform(put("/api/todo/1/mark/done"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.description", Matchers.is("Todo-1")))
		.andExpect(jsonPath("$.dueDateTime", Matchers.is("2023-06-06 03:14")))
		.andExpect(jsonPath("$.id", Matchers.is("1")))
		.andExpect(jsonPath("$.createdDateTime", Matchers.is("2023-05-31 03:14")))
		.andExpect(jsonPath("$.status", Matchers.is(TodoStatus.DONE.toString())));		
		verify(todoService, times(1)).updateTodoItem(any(TodoDto.class));

	}
	
	@Test
	public void testupdateTodoItemStatus_markAsNotDone() throws Exception {

		when(todoService.updateTodoItem(any(TodoDto.class))).thenReturn(todoDto);
		
		mockMvc.perform(put("/api/todo/1/mark/notdone"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.description", Matchers.is("Todo-1")))
		.andExpect(jsonPath("$.dueDateTime", Matchers.is("2023-06-06 03:14")))
		.andExpect(jsonPath("$.id", Matchers.is("1")))
		.andExpect(jsonPath("$.createdDateTime", Matchers.is("2023-05-31 03:14")))
		.andExpect(jsonPath("$.status", Matchers.is(TodoStatus.NOT_DONE.toString())));		
		verify(todoService, times(1)).updateTodoItem(any(TodoDto.class));

	}
}
