package ru.homework.repository;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import ru.homework.domain.Author;
import ru.homework.domain.Book;
import ru.homework.domain.Genre;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"test"})
public class BookRepositoryJpaTest {

	@Autowired
	private BookRepositoryJpa bookRepository;	

	private Book getTestBook1() {
		Set<Author> testBookAuthors = new HashSet<>();
		testBookAuthors.add(new Author(14, "Агафонов", "А", "В"));
		testBookAuthors.add(new Author(15, "Пожарская", "Светлана", "Георгиевна"));
		Genre testBookGenre = new Genre(3, "Детская литература");
		Book testBook = new Book("Фотобукварь", testBookAuthors, testBookGenre);
		int testBookId = bookRepository.insert(testBook);
		Book dbBook = bookRepository.getById(testBookId);
		assertEquals(testBook, dbBook);			
		return dbBook;
	}
	
	private Book getTestBook2() {	
		Set<Author> testBookAuthors = new HashSet<>();
		testBookAuthors.add(new Author(16, "Ткаченко", "Наталия", "Александровна"));
		testBookAuthors.add(new Author(17, "Тумановская", "Мария", "Петровна"));
		Genre testBookGenre = new Genre(3, "Детская литература");
		Book testBook = new Book("Букварь для малышей", testBookAuthors, testBookGenre);
		int testBookId = bookRepository.insert(testBook);
		return bookRepository.getById(testBookId);
	}	
	
	@Test
    @Transactional
    @Rollback(true)	
	public void testInsert() {
		getTestBook1();
	}
	
	@Test
    @Transactional
    @Rollback(true)	
	public void testUpdate() {
		Book testBook = getTestBook1();
		testBook.setName("ФОТО букварь");
		bookRepository.update(testBook);		
		Book dbBook = bookRepository.getById(testBook.getId());
		assertEquals(testBook.getName(), dbBook.getName());	
	}	
	
	@Test
    @Transactional
    @Rollback(true)	
	public void testGetByName() {
		Book testBook = getTestBook1();
		List<Book> testBookList = bookRepository.getByName("Фотобукварь");
		assertTrue(testBookList.size()>0);	
		assertTrue(testBookList.contains(testBook));			
	}	
	
	@Test
    @Transactional
    @Rollback(true)	
	public void testDelete() {
		Book testBook = getTestBook1();
		bookRepository.delete(testBook);
		Book dbBook = bookRepository.getById(testBook.getId());
		assertNull(dbBook);			
	}		
	
	@Test
    @Transactional
    @Rollback(true)	
	public void testCount() {
		int count = bookRepository.count();
		getTestBook1();
		assertTrue(bookRepository.count() == (count + 1));
	}
	
	@Test
    @Transactional
    @Rollback(true)	
	public void testGetAll() {
		Book book1 = getTestBook1();
		Book book2 = getTestBook2();
		HashMap<String, String> filters = new HashMap<>();
		filters.put("name", "букварь");
		List<Book> books = bookRepository.getAll(filters);	
		assertTrue(books.contains(book1));
		assertTrue(books.contains(book2));				
	}

}
