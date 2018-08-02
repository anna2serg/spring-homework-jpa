package ru.homework.dao;

import org.springframework.stereotype.Repository;
import ru.homework.domain.Person;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PersonRepositoryJpa implements PersonRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Person getById(int id) {
        return em.find(Person.class, id);
    }
}
