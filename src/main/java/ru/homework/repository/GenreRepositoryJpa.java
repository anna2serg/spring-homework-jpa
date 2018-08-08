package ru.homework.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.homework.domain.Genre;

@Transactional
@Repository
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private EntityManager em;	
	
	@Override
	public int count() {
    	Query query = em.createQuery("select count(g.genre_id) from Genre g");
        return (int) query.getSingleResult(); 	
	}

	@Override
	public int insert(Genre genre) {
		em.persist(genre);
		return genre.getId();
	}

	@Override
	public void update(Genre genre) {
		em.merge(genre);
	}

	@Override
	public Genre getById(int id) {
		return em.find(Genre.class, id);
	}

	@Override
	public Genre getByName(String name) {
		try {
	    	TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.name = :name", Genre.class);
	    	query.setParameter("name", name);
	    	return query.getSingleResult();  
		} catch (NoResultException e) {
			return null;
		}		
	}

	@Override
	public List<Genre> getAll(HashMap<String, String> filters) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Genre> genreCriteria = cb.createQuery(Genre.class);
		Root<Genre> genreRoot = genreCriteria.from(Genre.class);
		genreCriteria.select(genreRoot);
		
		if (filters.keySet().size() > 0) {
			final List<Predicate> predicates = new ArrayList<Predicate>();
			for (final Entry<String, String> e : filters.entrySet()) {

			    final String key = e.getKey();
			    final String value = e.getValue();

			    if ((key != null) && (value != null)) {
			    	predicates.add(cb.like(cb.lower(genreRoot.<String> get(key)), "%" + value.toLowerCase() + "%"));
			    }
			}
			
			genreCriteria.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));			
		}

		return em.createQuery(genreCriteria).getResultList();
	}

	@Override
	public void delete(Genre genre) {
		em.remove(genre);
	}

}
