package ru.homework.dao;

import java.util.List;

import ru.homework.domain.Person;

public interface PersonRepository {

    void insert(Person p);

    Person getById(int id);

    Person getFirst();

    List<Person> getAll();

    Person getByName(String name);
    
}
