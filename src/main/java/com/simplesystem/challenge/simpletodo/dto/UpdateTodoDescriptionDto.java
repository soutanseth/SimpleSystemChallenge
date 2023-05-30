package com.simplesystem.challenge.simpletodo.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateTodoDescriptionDto {

	@NotBlank
	private String description;
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

}
