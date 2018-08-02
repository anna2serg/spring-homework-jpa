package ru.homework.dao;

import ru.homework.domain.Person;

public interface PersonRepository {

    Person getById(int id);
}
