package ru.homework.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "emails")
public class Email {

    private int id;

    private String email;

    private List<Person> persons;
    
    public Email(String email, List<Person> persons) {
        this.email = email;
        this.persons = persons;
    }

    @Id
    @GeneratedValue    
    @Column(name = "email_id")    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }  

    @Column(name = "email")   
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }    
    
    @ManyToMany(mappedBy = "emails")
    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }    
}
