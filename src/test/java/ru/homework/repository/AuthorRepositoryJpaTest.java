package ru.homework.repository;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import ru.homework.domain.Author;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"test"})
public class AuthorRepositoryJpaTest {

	@Autowired
	private AuthorRepositoryJpa authorRepository;
	
	@Test
    @Transactional
    @Rollback(true)	
	public void testInsert() {
		Author testAuthor = new Author("Достоевский", "Федор", "Михайлович");
		int testAuthorId = authorRepository.insert(testAuthor);
		Author dbAuthor = authorRepository.getById(testAuthorId);
		assertEquals(testAuthor, dbAuthor);		
	}
	
	@Test
    @Transactional
    @Rollback(true)	
	public void testUpdate() {
		Author testAuthor = new Author("Достоевский", "Федор", "Михайлович");
		authorRepository.insert(testAuthor);
		testAuthor.setFirstname("Федя");
		authorRepository.update(testAuthor);
		Author dbAuthor = authorRepository.getById(testAuthor.getId());
		assertEquals(testAuthor.getFirstname(), dbAuthor.getFirstname());			
	}	
	
	@Test
    @Transactional
    @Rollback(true)	
	public void testDelete() {
		Author testAuthor = new Author("Достоевский", "Федор", "Михайлович");
		int testAuthorId = authorRepository.insert(testAuthor);
		authorRepository.delete(testAuthor);
		Author dbAuthor = authorRepository.getById(testAuthorId);
		assertNull(dbAuthor);			
	}		
	
	@Test
    @Transactional
    @Rollback(true)	
	public void testCount() {
		int count = authorRepository.count();
		Author testAuthor = new Author("Достоевский", "Федор", "Михайлович");
		authorRepository.insert(testAuthor);
		assertTrue(authorRepository.count() == (count + 1));		
	}

	@Test
    @Transactional
    @Rollback(true)	
	public void testGetAll() {
		Author author1 = new Author("Салтыков-Щедрин", "Михаил", "Евграфович");
		authorRepository.insert(author1);
		Author author2 = new Author("Достоевский", "Федя", "Михайлович");
		authorRepository.insert(author2);		
		HashMap<String, String> filters = new HashMap<>();
		filters.put("name", "миха");
		List<Author> authors = authorRepository.getAll(filters);
		assertTrue(authors.contains(author1));
		assertTrue(authors.contains(author2));			
	}	
	
	@Test
    @Transactional
    @Rollback(true)	
	public void testGetByNames() {
		Author testAuthor = new Author("Достоевский", "Федя", "Михайлович");
		authorRepository.insert(testAuthor);
		List<Author> DostoevskyList = authorRepository.getByNames("Достоевский", "Федя", "Михайлович");
		assertTrue(DostoevskyList.size()>0);
		assertTrue(DostoevskyList.contains(testAuthor));	
	}

}
