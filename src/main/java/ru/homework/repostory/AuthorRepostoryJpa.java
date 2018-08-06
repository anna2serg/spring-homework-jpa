package ru.homework.repostory;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.homework.domain.Author;

@Transactional
@Repository
public class AuthorRepostoryJpa implements AuthorRepostory {

    @PersistenceContext
    private EntityManager em;		
	
	@Override
	public int count() {
    	Query query = em.createQuery("select count(a.author_id) from Author a");
        return (int) query.getSingleResult(); 
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
	    	sql += (middlename==null) ? " and a.middlename IS NULL " : " and lower(a.middlename) = :middlename ";	    	
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Author author) {
		em.remove(author);
	}

}
