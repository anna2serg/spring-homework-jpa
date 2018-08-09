package ru.homework.repository;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

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

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
	InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
	ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})	
public class AuthorRepositoryJpaTest {

	@Autowired
	private AuthorRepositoryJpa authorRepository;
	
	@Test
    @Transactional
    @Rollback(true)	
	public void test() {
		int count = authorRepository.count();
		Author testAuthor = new Author("Достоевский", "Федор", "Михайлович");
		int testAuthorId = authorRepository.insert(testAuthor);
		assertTrue(authorRepository.count() == (count + 1));
		Author dbAuthor = authorRepository.getById(testAuthorId);
		assertEquals(testAuthor, dbAuthor);
		testAuthor.setFirstname("Федя");
		authorRepository.update(testAuthor);
		dbAuthor = authorRepository.getById(testAuthor.getId());
		assertEquals(testAuthor.getFirstname(), dbAuthor.getFirstname());	
		List<Author> DostoevskyList = authorRepository.getByNames("Достоевский", "Федя", "Михайлович");
		assertTrue(DostoevskyList.size()>0);
		dbAuthor = DostoevskyList.get(0);
		assertEquals(testAuthor, dbAuthor);
		testAuthor = new Author("Салтыков-Щедрин", "Михаил", "Евграфович");
		testAuthorId = authorRepository.insert(testAuthor);
		HashMap<String, String> filters = new HashMap<>();
		filters.put("name", "миха");
		List<Author> authors = authorRepository.getAll(filters);
		assertTrue(authors.contains(testAuthor));
		assertTrue(authors.contains(dbAuthor));	
		authorRepository.delete(testAuthor);
		dbAuthor = authorRepository.getById(testAuthorId);
		assertNull(dbAuthor);		
	}

}
