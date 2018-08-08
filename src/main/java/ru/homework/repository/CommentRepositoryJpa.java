package ru.homework.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.homework.domain.Comment;

@Transactional
@Repository
public class CommentRepositoryJpa implements CommentRepository {
	
    @PersistenceContext
    private EntityManager em;		
	
	@Override
	public int insert(Comment comment) {
		em.persist(comment);
		return comment.getId();
	}

}
