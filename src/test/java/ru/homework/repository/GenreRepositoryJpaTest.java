package ru.homework.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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

import ru.homework.domain.Genre;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
	InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
	ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})	
public class GenreRepositoryJpaTest {
	
	@Autowired
	private GenreRepositoryJpa genreRepository;	

	@Test
    @Transactional
    @Rollback(true)	
	public void testInsert() {
		Genre testGenre = new Genre("Изобразительное искусство");
		int testGenreId = genreRepository.insert(testGenre);
		Genre dbGenre = genreRepository.getById(testGenreId);
		assertEquals(testGenre, dbGenre);
	}		
	
	@Test
    @Transactional
    @Rollback(true)	
	public void testUpdate() {
		Genre testGenre = new Genre("Изобразительное искусство");
		genreRepository.insert(testGenre);		
		testGenre.setName("Изобразительное искусство и фотография");
		genreRepository.update(testGenre);
		Genre dbGenre = genreRepository.getById(testGenre.getId());
		assertEquals(testGenre.getName(), dbGenre.getName());	
		dbGenre = genreRepository.getByName("Изобразительное искусство и фотография");
		assertEquals(testGenre, dbGenre);		
	}		
		
	@Test
    @Transactional
    @Rollback(true)	
	public void testDelete() {
		Genre testGenre = new Genre("Изобразительное искусство");
		int testGenreId = genreRepository.insert(testGenre);
		genreRepository.delete(testGenre);
		Genre dbGenre = genreRepository.getById(testGenreId);
		assertNull(dbGenre);
	}		
	
	@Test
    @Transactional
    @Rollback(true)	
	public void testCount() {
		int count = genreRepository.count();
		Genre testGenre = new Genre("Изобразительное искусство");
		genreRepository.insert(testGenre);
		assertTrue(genreRepository.count() == (count + 1));
	}	
	
	@Test
    @Transactional
    @Rollback(true)	
	public void testGetAll() {
		Genre genre1 = new Genre("Биографии и мемуары"); 
		genreRepository.insert(genre1);
		Genre genre2 = new Genre("Изобразительное искусство и фотография");
		genreRepository.insert(genre2);
		HashMap<String, String> filters = new HashMap<>();
		filters.put("name", "граф");
		List<Genre> genres = genreRepository.getAll(filters);
		assertTrue(genres.contains(genre1));
		assertTrue(genres.contains(genre2));
	}
	
}
