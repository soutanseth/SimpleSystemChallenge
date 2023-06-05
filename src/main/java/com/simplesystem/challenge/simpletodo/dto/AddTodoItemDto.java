package com.simplesystem.challenge.simpletodo.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddTodoItemDto {
	
	@NotBlank
	private String description;
	
	@NotNull
	@Future
	private LocalDateTime dueDateTime;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getDueDateTime() {
		return dueDateTime;
	}

	public void setDueDateTime(LocalDateTime dueDateTime) {
		this.dueDateTime = dueDateTime;
	}
	
	
	
}
