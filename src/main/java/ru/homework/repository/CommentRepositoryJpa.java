package ru.homework.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.homework.domain.Comment;

@Transactional
@Repository
public class CommentRepositoryJpa implements CommentRepository {
	
    @PersistenceContext
    private EntityManager em;		
	
	@Override
	public int count() {
		TypedQuery<Long> query = em.createQuery("select count(c.id) from Comment c", Long.class);
        return query.getSingleResult().intValue(); 	
	}    
    
	@Override
	public int insert(Comment comment) {
		em.persist(comment);
		return comment.getId();
	}

	@Override
	public List<Comment> getAll(HashMap<String, String> filters) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Comment> commentCriteria = cb.createQuery(Comment.class);
		Root<Comment> commentRoot = commentCriteria.from(Comment.class);
		commentCriteria.select(commentRoot);
		
		if (filters.keySet().size() > 0) {
			final List<Predicate> predicates = new ArrayList<Predicate>();
			for (final Entry<String, String> e : filters.entrySet()) {

			    final String key = e.getKey();
			    final String value = e.getValue();

			    if ((key != null) && (value != null)) {
			    	predicates.add(cb.like(cb.lower(commentRoot.<String> get(key)), "%" + value.toLowerCase() + "%"));
			    }
			}
			
			commentCriteria.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));			
		}

		return em.createQuery(commentCriteria).getResultList();
	}

}
