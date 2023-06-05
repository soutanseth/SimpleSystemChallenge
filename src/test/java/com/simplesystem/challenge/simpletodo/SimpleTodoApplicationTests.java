package com.simplesystem.challenge.simpletodo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.simplesystem.challenge.simpletodo.controllers.TodoController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SimpleTodoApplicationTests {
	
	@Autowired
	private TodoController todoController;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(todoController);
	}

}
