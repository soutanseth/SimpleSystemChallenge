package com.simplesystem.challenge.simpletodo.dto;

import com.simplesystem.challenge.simpletodo.models.TodoStatus;
import com.simplesystem.challenge.simpletodo.validation.ValidateTodoDtoStatus;

public class UpdateTodoStatusDto {

	@ValidateTodoDtoStatus
	private TodoStatus status;

	public TodoStatus getStatus() {
		return status;
	}

	public void setStatus(TodoStatus status) {
		this.status = status;
	}
}
