package ru.homework.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "comments")
public class Comment {
    private int id;
    private Book book;    
    private String commentator = "Anonym";
    private String content;
    private short score;
    
    public Comment() {
        super();
    } 

    public Comment(Book book, String commentator, String content, short score) {
        this(0, book, commentator, content, score);
    }       
    
    public Comment(int id, Book book, String commentator, String content, short score) {
        super();
        this.id = id;
        this.book = book;
        this.commentator = commentator;
        this.content = content;
        this.setScore(score);
    }        

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_generator")  
    @SequenceGenerator(name="comment_generator", sequenceName = "comment_seq", initialValue = 100, allocationSize=1)
    @Column(name = "comment_id")    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE })
    @JoinColumn(name="book_id")
    public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getCommentator() {
        return commentator;
    }

    public void setCommentator(String commentator) {
        this.commentator = commentator;
    }    
    
    public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public short getScore() {
		return score;
	}

	public void setScore(short score) {
		this.score = score;
	}

	@Override
    public String toString() {
        return String.format("[%s] %s: (%s) \"%s\"", id, commentator, score, content);
    }    
 

        
}
