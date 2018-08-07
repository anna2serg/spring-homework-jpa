package ru.homework.repostory;

import java.util.HashMap;
import java.util.List;

import ru.homework.domain.Book;

public interface BookRepostory {
	
	int count();
	int insert(Book book);
	void update(Book book);
	Book getById(int id);
	List<Book> getAll(HashMap<String, String> filters);
	List<Book> getByName(String name);
	void delete(Book book);
	
}
