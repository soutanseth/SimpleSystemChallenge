package com.simplesystem.challenge.simpletodo.validation.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.simplesystem.challenge.simpletodo.exception.TodoException;
import com.simplesystem.challenge.simpletodo.models.TodoStatus;

public class ValidationUtil {

	public static TodoStatus validateStatusStrAndGet(String statusStr) throws TodoException {
		Pattern notDonePattern = Pattern.compile("[nN][oO][tT][-_ ]?[dD][oO][nN][eN]");
		Pattern donePattern = Pattern.compile("[dD][oO][nN][eN]");
		Matcher m=notDonePattern.matcher(statusStr);
		if (m.matches()) {
			return TodoStatus.NOT_DONE;
		}
		m=donePattern.matcher(statusStr);
		if (m.matches()) {
			return TodoStatus.DONE;
		}
		throw new TodoException("invalid status. only done and not done is permitted");
	}
}
