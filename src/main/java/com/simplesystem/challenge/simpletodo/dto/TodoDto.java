package com.simplesystem.challenge.simpletodo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.simplesystem.challenge.simpletodo.models.TodoStatus;

@JsonInclude(Include.NON_NULL)
public class TodoDto {

	private String id;
	
	private String description;
	
	private TodoStatus status;
	
	private LocalDateTime createdDateTime;
	
	private LocalDateTime dueDateTime;
	
	private LocalDateTime completedDateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TodoStatus getStatus() {
		return status;
	}

	public void setStatus(TodoStatus status) {
		this.status = status;
	}

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public LocalDateTime getDueDateTime() {
		return dueDateTime;
	}

	public void setDueDateTime(LocalDateTime dueDateTime) {
		this.dueDateTime = dueDateTime;
	}

	public LocalDateTime getCompletedDateTime() {
		return completedDateTime;
	}

	public void setCompletedDateTime(LocalDateTime completedDateTime) {
		this.completedDateTime = completedDateTime;
	}

	@Override
	public String toString() {
		return "TodoDto [id=" + id + ", description=" + description + ", status=" + status + ", createdDateTime="
				+ createdDateTime + ", dueDateTime=" + dueDateTime + ", completedDateTime=" + completedDateTime + "]";
	}
	
	
}
