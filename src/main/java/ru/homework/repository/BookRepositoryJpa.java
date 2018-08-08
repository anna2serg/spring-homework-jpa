package ru.homework.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.homework.domain.Author;
import ru.homework.domain.Book;
import ru.homework.domain.Genre;

@Transactional
@Repository
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private EntityManager em;		
	
	@Override
	public int count() {
    	Query query = em.createQuery("select count(b.book_id) from Book b");
        return (int) query.getSingleResult(); 
	}

	@Override
	public int insert(Book book) {
		em.persist(book);
		return book.getId();
	}

	@Override
	public void update(Book book) {
		em.merge(book);
	}

	@Override
	public Book getById(int id) {
		return em.find(Book.class, id);
	}

	@Override
	public List<Book> getAll(HashMap<String, String> filters) {
			
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Book> bookCriteria = cb.createQuery(Book.class);
		Root<Book> bookRoot = bookCriteria.from(Book.class);
		Join<Book, Genre> withGenreJoin = bookRoot.join("genre", JoinType.LEFT);
		Join<Book, Author> withAuthorJoin = bookRoot.join("authors", JoinType.LEFT);

		final List<Predicate> predicates = new ArrayList<Predicate>();	
		predicates.add(cb.equal(withGenreJoin.get("name"), "Учебная литература"));
		predicates.add(cb.like(cb.lower(withAuthorJoin.get("surname")), "%не%"));
		bookCriteria.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		//bookCriteria.select(bookRoot).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));		
		/*if (filters.keySet().size() > 0) {
			final List<Predicate> predicates = new ArrayList<Predicate>();		
			for (final Entry<String, String> filter : filters.entrySet()) {
			    final String value = filter.getValue();
			    if ((value != null)) {
			    	predicates.add(cb.like(cb.lower(bookRoot.get("surname")), "%" + value.toLowerCase() + "%"));
			    	predicates.add(cb.like(cb.lower(bookRoot.get("firstname")), "%" + value.toLowerCase() + "%"));
			    	predicates.add(cb.like(cb.lower(bookRoot.get("middlename")), "%" + value.toLowerCase() + "%"));
			    }			
			}
			bookCriteria.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));					
		}*/ 
		
		return em.createQuery(bookCriteria).getResultList();
	}

	@Override
	public List<Book> getByName(String name) {
		try {
	    	TypedQuery<Book> query = em.createQuery("select b from Book b where b.name = :name", Book.class);
	    	query.setParameter("name", name);
	    	return query.getResultList();  
		} catch (NoResultException e) {
			return null;
		}		
	}

	@Override
	public void delete(Book book) {
		em.remove(book);
	}

}
