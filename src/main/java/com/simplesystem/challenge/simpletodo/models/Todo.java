package com.simplesystem.challenge.simpletodo.models;


import java.time.LocalDateTime;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Todo {

	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
	
	@NotNull
	private String description;
	
	@NotNull
	private TodoStatus status;
	
	@NotNull
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
		return "Todo [id=" + id + ", description=" + description + ", status=" + status + ", createdDateTime="
				+ createdDateTime + ", dueDateTime=" + dueDateTime + ", completedDateTime=" + completedDateTime + "]";
	}
	
	
	
	
}
