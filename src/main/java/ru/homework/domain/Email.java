package ru.homework.domain;

import java.util.Set;

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

    private Set<Person> persons;
    
    public Email(String email/*, List<Person> persons*/) {
        this.email = email;
        //this.persons = persons;
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
    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }    
}
