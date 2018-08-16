package ru.homework.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

import ru.homework.domain.Book;
import ru.homework.domain.Comment;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"test"})
public class CommentRepositoryJpaTest {

	@Autowired
	private CommentRepositoryJpa commentRepository;		

	private Book getTestBook() {
		HashMap<String, String> filters = new HashMap<String, String>(); 
		List<Comment> comments = commentRepository.getAll(filters);
		return comments.get(0).getBook();
	}
	
	@Test
    @Transactional
    @Rollback(true)	
	public void testInsert() {
		Comment testComment = new Comment(getTestBook(), (short) 4, "Бу", "Каспер");
		int testCommentId = commentRepository.insert(testComment);
		Comment dbComment = commentRepository.getById(testCommentId);
		assertEquals(testComment, dbComment);	
	}
	
	@Test
    @Transactional
    @Rollback(true)	
	public void testCount() {
		int count = commentRepository.count();
		Comment testComment = new Comment(getTestBook(), (short) 4, "Бу", "Каспер");
		commentRepository.insert(testComment);	
		assertTrue(commentRepository.count() == (count + 1));
	}
	
	@Test
    @Transactional
    @Rollback(true)	
	public void testGetAll() {
		Comment testComment = new Comment(getTestBook(), (short) 4, "Бу", "Каспер");
		commentRepository.insert(testComment);		
		HashMap<String, String> filters = new HashMap<String, String>(); 
		filters.put("commentator", "Каспер");
		List<Comment> comments = commentRepository.getAll(filters);		
		assertTrue(comments.contains(testComment));
	}

}
