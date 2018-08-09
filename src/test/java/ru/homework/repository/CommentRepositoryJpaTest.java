package ru.homework.repository;

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

import ru.homework.domain.Comment;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
	InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
	ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})	
public class CommentRepositoryJpaTest {

	@Autowired
	private CommentRepositoryJpa commentRepository;		
	
	@Test
    @Transactional
    @Rollback(true)	
	public void testInsertAndCount() {
		int count = commentRepository.count();
		HashMap<String, String> filters = new HashMap<String, String>(); 
		List<Comment> comments = commentRepository.getAll(filters);
		assertTrue(comments.size() == count);
	}

}
