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
	public void test() {
		int count = genreRepository.count();
		Genre testGenre = new Genre("Изобразительное искусство");
		int testGenreId = genreRepository.insert(testGenre);
		assertTrue(genreRepository.count() == (count + 1));
		Genre dbGenre = genreRepository.getById(testGenreId);
		assertEquals(testGenre, dbGenre);
		testGenre.setName("Изобразительное искусство и фотография");
		genreRepository.update(testGenre);
		dbGenre = genreRepository.getById(testGenre.getId());
		assertEquals(testGenre.getName(), dbGenre.getName());
		dbGenre = genreRepository.getByName("Изобразительное искусство и фотография");
		assertEquals(testGenre, dbGenre);
		testGenre = new Genre("Биографии и мемуары"); 
		testGenreId = genreRepository.insert(testGenre);
		HashMap<String, String> filters = new HashMap<>();
		filters.put("name", "граф");
		List<Genre> genres = genreRepository.getAll(filters);
		assertTrue(genres.contains(testGenre));
		assertTrue(genres.contains(dbGenre));
		genreRepository.delete(testGenre);
		dbGenre = genreRepository.getById(testGenreId);
		assertNull(dbGenre);
	}

}
