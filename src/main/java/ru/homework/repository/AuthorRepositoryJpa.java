package ru.homework.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.homework.domain.Author;

@Transactional
@Repository
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private EntityManager em;		
	
	@Override
	public int count() {
		TypedQuery<Long> query = em.createQuery("select count(a.id) from Author a", Long.class);
        return query.getSingleResult().intValue(); 			
	}

	@Override
	public int insert(Author author) {
		em.persist(author);
		return author.getId();
	}

	@Override
	public void update(Author author) {
		em.merge(author);
	}

	@Override
	public Author getById(int id) {
		return em.find(Author.class, id);
	}

	@Override
	public List<Author> getByNames(String surname, String firstname, String middlename) {
		try {    	
	    	String sql = "select a from Author a "
					   + "where lower(a.surname) = :surname "
				  	   + "and lower(a.firstname) = :firstname ";
	    	sql += (middlename==null) ? 
	    			   " and a.middlename IS NULL " : " and lower(a.middlename) = :middlename ";	    	
	    	TypedQuery<Author> query = em.createQuery(sql, Author.class);
	    	query.setParameter("surname", surname.trim().toLowerCase());
	    	query.setParameter("firstname", firstname.trim().toLowerCase());
	    	if (middlename!=null) query.setParameter("middlename", middlename.trim().toLowerCase());     	
	    	return query.getResultList(); 	    	

		} catch (NoResultException e) {
			return null;
		}	
	}

	@Override
	public List<Author> getAll(HashMap<String, String> filters) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Author> authorCriteria = cb.createQuery(Author.class);
		Root<Author> authorRoot = authorCriteria.from(Author.class);
		authorCriteria.select(authorRoot);		
		
		if (filters.keySet().size() > 0) {
			final List<Predicate> predicates = new ArrayList<Predicate>();		
			for (final Entry<String, String> filter : filters.entrySet()) {
			    final String value = filter.getValue();
			    if ((value != null)) {
			    	predicates.add(cb.like(cb.lower(authorRoot.get("surname")), "%" + value.toLowerCase() + "%"));
			    	predicates.add(cb.like(cb.lower(authorRoot.get("firstname")), "%" + value.toLowerCase() + "%"));
			    	predicates.add(cb.like(cb.lower(authorRoot.get("middlename")), "%" + value.toLowerCase() + "%"));
			    }			
			}
			authorCriteria.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));					
		} 
		
		return em.createQuery(authorCriteria).getResultList();
	}

	@Override
	public void delete(Author author) {
		em.remove(author);
	}

}
