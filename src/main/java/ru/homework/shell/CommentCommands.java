package ru.homework.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import ru.homework.exception.EntityNotFoundException;
import ru.homework.exception.InvalidValueFormatException;
import ru.homework.exception.NotUniqueEntityFoundException;
import ru.homework.service.BookcardService;
import ru.homework.service.FetchDataService;

@ShellComponent
public class CommentCommands {
	
	private final BookcardService service;
	private final FetchDataService fetcher;
	
	@Autowired
    public CommentCommands(BookcardService service, FetchDataService fetcher) {
		this.service = service;		
		this.fetcher = fetcher;
	}	
	
    @ShellMethod(value = "добавить комментарий к книге")
    public void addComment(
    		@ShellOption(help="ИД или наименование книги") String book,
    		@ShellOption(help="оценка читателя: 0-5") String score,
    		@ShellOption(help="комментарий", defaultValue=ShellOption.NULL) String content,
    		@ShellOption(help="комментатор", defaultValue=ShellOption.NULL) String commentator) {   	
        try {
        	System.out.println(service.addComment(book, score, content, commentator));		  	
		} catch (EntityNotFoundException | NotUniqueEntityFoundException | InvalidValueFormatException e) {
			System.out.println(e.getMessage());
		}
    }   	
	
}
