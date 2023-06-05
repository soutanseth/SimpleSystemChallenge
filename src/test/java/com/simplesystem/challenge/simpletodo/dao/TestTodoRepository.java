package com.simplesystem.challenge.simpletodo.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.simplesystem.challenge.simpletodo.models.Todo;
import com.simplesystem.challenge.simpletodo.models.TodoStatus;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestTodoRepository {

	@Autowired
	TodoRepository todoRepository;

	@Test
	public void testSave() {
		Todo todo = new Todo();
		todo.setDescription("Todo-1");
		todo.setStatus(TodoStatus.NOT_DONE);

		todoRepository.save(todo);

		List<Todo> todoItems = todoRepository.findAll();
		Assertions.assertIterableEquals(todoItems, Arrays.asList(todo));
		assertThat(todoItems).extracting(Todo::getDescription).contains("Todo-1");

		todoRepository.deleteAll();
	}
	
	@Test
	public void testFindById() {
		Todo todo = new Todo();
		todo.setDescription("Todo-1");
		Todo todo1 =todoRepository.save(todo);

		Optional<Todo> todoItem = todoRepository.findById(todo1.getId());
		assertEquals(todoItem.isEmpty(), false);
		assertEquals(todoItem.get().getDescription(), "Todo-1");
		todoRepository.deleteAll();
	}
	
	@Test
	public void testFindByStatus() {
		Todo todo = new Todo();
		todo.setDescription("Todo-1");
		todo.setStatus(TodoStatus.NOT_DONE);
		todoRepository.save(todo);

		List<Todo> todoItems = todoRepository.findByStatus(TodoStatus.NOT_DONE);
		Assertions.assertIterableEquals(todoItems, Arrays.asList(todo));
		assertThat(todoItems).extracting(Todo::getStatus).contains(TodoStatus.NOT_DONE);
		todoRepository.deleteAll();
	}
}
