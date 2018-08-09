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
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import ru.homework.domain.Author;
import ru.homework.domain.Book;
import ru.homework.domain.Genre;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
	InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
	ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})	
public class BookRepositoryJpaTest {

	@Autowired
	private BookRepositoryJpa bookRepository;	
	
	@Test
    @Transactional
    @Rollback(true)	
	public void test() {
		int count = bookRepository.count();
		Set<Author> testBookAuthors = new HashSet<>();
		testBookAuthors.add(new Author(14, "Агафонов", "А", "В"));
		testBookAuthors.add(new Author(15, "Пожарская", "Светлана", "Георгиевна"));
		Genre testBookGenre = new Genre(3, "Детская литература");
		Book testBook = new Book("Фотобукварь", testBookAuthors, testBookGenre);
		int testBookId = bookRepository.insert(testBook);
		assertTrue(bookRepository.count() == (count + 1));
		Book dbBook = bookRepository.getById(testBookId);
		assertEquals(testBook, dbBook);		
		testBook.setName("ФОТО букварь");
		bookRepository.update(testBook);		
		dbBook = bookRepository.getById(testBook.getId());
		assertEquals(testBook.getName(), dbBook.getName());		
		List<Book> testBookList = bookRepository.getByName("ФОТО букварь");
		assertTrue(testBookList.size()>0);		
		dbBook = testBookList.get(0);
		assertEquals(testBook, dbBook);	
		testBookAuthors.clear();;
		testBookAuthors.add(new Author(16, "Ткаченко", "Наталия", "Александровна"));
		testBookAuthors.add(new Author(17, "Тумановская", "Мария", "Петровна"));
		testBook = new Book("Букварь для малышей", testBookAuthors, testBookGenre);
		testBookId = bookRepository.insert(testBook);
		HashMap<String, String> filters = new HashMap<>();
		filters.put("name", "букварь");
		List<Book> books = bookRepository.getAll(filters);	
		assertTrue(books.contains(testBook));
		assertTrue(books.contains(dbBook));		
		bookRepository.delete(testBook);
		dbBook = bookRepository.getById(testBookId);
		assertNull(dbBook);			
	}

}
