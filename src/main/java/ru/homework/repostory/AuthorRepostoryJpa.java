package ru.homework.repostory;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.homework.domain.Author;

@Transactional
@Repository
public class AuthorRepostoryJpa implements AuthorRepostory {

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Author author) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(Author author) {
		// TODO Auto-generated method stub

	}

	@Override
	public Author getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Author> getByNames(String surname, String firstname, String middlename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Author> getAll(HashMap<String, String> filters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub

	}

}
