package ru.homework.repostory;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.homework.domain.Book;

@Transactional
@Repository
public class BookRepostoryJpa implements BookRepostory {

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Book book) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(Book book) {
		// TODO Auto-generated method stub

	}

	@Override
	public Book getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getAll(HashMap<String, String> filters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub

	}

}
