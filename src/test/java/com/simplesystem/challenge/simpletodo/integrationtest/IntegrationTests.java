package com.simplesystem.challenge.simpletodo.integrationtest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.simplesystem.challenge.simpletodo.config.DateFormatterConfig;
import com.simplesystem.challenge.simpletodo.config.ModelMapperConfig;
import com.simplesystem.challenge.simpletodo.controllers.TodoController;
import com.simplesystem.challenge.simpletodo.dto.AddTodoItemDto;
import com.simplesystem.challenge.simpletodo.dto.TodoDto;
import com.simplesystem.challenge.simpletodo.dto.UpdateTodoDescriptionDto;
import com.simplesystem.challenge.simpletodo.exception.TodoException;
import com.simplesystem.challenge.simpletodo.models.TodoStatus;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Import({ModelMapperConfig.class, DateFormatterConfig.class})
public class IntegrationTests {

	@Autowired
	TodoController todoController;

	@Test
	public void testCreateReadUpdate() {
		AddTodoItemDto addTodoDto= new AddTodoItemDto();
		addTodoDto.setDescription("Todo-1");
		addTodoDto.setDueDateTime(LocalDateTime.parse("2023-06-06 03:14", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

		TodoDto todoResult = todoController.addTodoItem(addTodoDto);
		assertNotNull(todoResult);

		List<TodoDto> todoItems = todoController.getAllTodoItems();
		assertNotNull(todoItems);
		assertEquals(todoItems.size(), 1);
		assertThat(todoItems).extracting(TodoDto::getDescription).contains("Todo-1");
		
		UpdateTodoDescriptionDto updateTodoDescriptionDto = new UpdateTodoDescriptionDto();
		updateTodoDescriptionDto.setDescription("Todo-1(Updated)");
		try {
			TodoDto udpatedDto = todoController.updateTodoItem(todoResult.getId(), updateTodoDescriptionDto);
			assertNotNull(udpatedDto);
			assertEquals(udpatedDto.getDescription(), "Todo-1(Updated)");
		} catch (TodoException e) {
			e.printStackTrace();
		}
		
		try {
			TodoDto udpatedDto =todoController.updateTodoItemStatus(todoResult.getId(), "done");
			assertNotNull(udpatedDto);
			assertEquals(udpatedDto.getStatus(), TodoStatus.DONE);
			assertNotNull(udpatedDto.getCompletedDateTime());
		} catch (TodoException e) {
			e.printStackTrace();
		}
		
	}
}
