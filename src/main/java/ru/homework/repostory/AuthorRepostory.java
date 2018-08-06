package ru.homework.repostory;

import java.util.HashMap;
import java.util.List;

import ru.homework.domain.Author;

public interface AuthorRepostory {
	
	int count();
	int insert(Author author);
	void update(Author author);
	Author getById(int id);
	List<Author> getByNames(String surname, String firstname, String middlename);
	List<Author> getAll(HashMap<String, String> filters);
	void delete(Author author);
	
}
