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
		String sqlAllGenre = "select g from Genre g ";
		String sqlWhere = "";
		String sqlOrder = " order by g.name ";
		String sql = "";
		if (filters.get("name") != null && !filters.get("name").toString().isEmpty()) {
			sqlWhere = " where LOWER(g.name) like lower(concat('%', :name,'%')) ";	
		} 	 				
		sql = sqlAllGenre + sqlWhere + sqlOrder;
    	TypedQuery<Genre> query = em.createQuery(sql, Genre.class);
    	if (filters.get("name") != null && !filters.get("name").toString().isEmpty()) {
    		query.setParameter("name", filters.get("name"));
    	}   	
        return query.getResultList();
	}

	@Override
	public void delete(Genre genre) {
		em.remove(genre);
	}

}
