package ru.homework.repository;

import ru.homework.domain.Comment;

public interface CommentRepository {
	
	int insert(Comment comment);
	
}