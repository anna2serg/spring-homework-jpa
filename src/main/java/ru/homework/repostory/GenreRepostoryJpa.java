package ru.homework.repostory;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.homework.domain.Genre;

@Transactional
@Repository
public class GenreRepostoryJpa implements GenreRepostory {

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
		if (filters.get("name") != null && !filters.get("name").toString().isEmpty()) {
			genreCriteria.where(cb.like(cb.lower(genreRoot.get("name")), "%" + filters.get("name").toString().toLowerCase() + "%"));
		}
		return em.createQuery(genreCriteria).getResultList();
	}

	@Override
	public void delete(Genre genre) {
		em.remove(genre);
	}

}
