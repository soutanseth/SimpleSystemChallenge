package com.simplesystem.challenge.simpletodo.validation;

import com.simplesystem.challenge.simpletodo.models.TodoStatus;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UpdateTodoStatusValidator implements  ConstraintValidator<ValidateTodoDtoStatus, TodoStatus>{
	
	@Override
	public boolean isValid(TodoStatus value, ConstraintValidatorContext context) {
		if(value==TodoStatus.DONE || value==TodoStatus.NOT_DONE)
			return true;
		else
			return false;
	}

}
