package ru.homework.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ru.homework.helper.StringHelper;

@Entity
@Table(name = "books")
public class Book {
    private int id;
    private String name;
    private Set<Author> authors;
    private Genre genre;
    
    public Book(String name, Set<Author> authors, Genre genre) {
        this(0, name, authors, genre);
    }            
 
    public Book(int id, String name, Set<Author> authors, Genre genre) {
        super();
        this.id = id;
        this.name = name;
        this.authors = authors;
        this.genre = genre;
    }        

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")  
    @SequenceGenerator(name="book_generator", sequenceName = "book_seq", allocationSize=1)    
    @Column(name = "book_id")    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "books_authors", 
            joinColumns = { @JoinColumn(name = "book_id") }, 
            inverseJoinColumns = { @JoinColumn(name = "author_id") }
        )    
    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
    	this.authors = authors;
    }    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }      
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="genre_id")
    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }    
    
    @Override
    public String toString() {
    	String result = "";
    	String book_name = String.format("[%s] %s", id, name);
    	book_name = StringHelper.ellipsize(book_name, 50);
    	result += String.format("%-50s", book_name);
    	result += "| "; 
    	String book_genre = StringHelper.ellipsize(genre.toString(), 25);
    	result += String.format("%-25s", book_genre);
    	result += "| ";
    	boolean isFirstAuthor = true;
    	for (Author author : authors) {
    		if (isFirstAuthor) {
    			result += String.format("%-35s\r\n", author);
    		} else {
            	result += String.format("%-50s", "");
            	result += "| ";
            	result += String.format("%-25s", "");
            	result += "| ";
            	result += String.format("%-35s\r\n", author);    		  			
    		}
    		isFirstAuthor = false;	
    	}

        return result; 
    }   
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }  	
        if (!(obj instanceof Book)) {
            return false;
        }       
        Book other = (Book)obj;
        
        return (other.id == this.id) && 
			 (other.name.equals(this.name)) && 
			 (other.genre.equals(this.genre)) &&
			 (other.authors.size() == this.authors.size() &&
			 (other.authors.containsAll(this.authors)) &&
			 (this.authors.containsAll(other.authors)));    
    }    
    
}
