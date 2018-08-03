package ru.homework.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "person")
public class Person {

    private int id;
    private String name;
    
    private Set<Email> emails;

    public Person() {
    }        
    
    public Person(String name, Set<Email> emails) {
        this.name = name;
        this.emails = emails;
    }

    @Id
    @GeneratedValue    
    @Column(name = "person_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "person_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "person_email", 
            joinColumns = { @JoinColumn(name = "person_id") }, 
            inverseJoinColumns = { @JoinColumn(name = "email_id") }
        )
    public Set<Email> getEmails() {
        return emails;
    }

    public void setEmails(Set<Email> emails) {
        this.emails = emails;
    }
    
    @Override
    public String toString() {
        return this.name;
    }        
    
}
