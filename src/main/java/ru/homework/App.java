package ru.homework;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import ru.homework.dao.PersonRepository;
import ru.homework.domain.Email;
import ru.homework.domain.Person;

@ComponentScan("ru.homework")
@SpringBootApplication
public class App {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(App.class, args);
		
        PersonRepository repository = context.getBean(PersonRepository.class);

        Person nullPerson = repository.getById(1);
        
        System.out.println(nullPerson);
        
        Set<Email> mikeEmails = new HashSet<Email>();    
        Email mikeEmail1 = new Email("mike1"); 
        mikeEmails.add(mikeEmail1);
        Email mikeEmail2 = new Email("mike2"); 
        mikeEmails.add(mikeEmail2);
        Person newPerson = new Person("Mike", mikeEmails) ;
        repository.insert(newPerson);
        
        Set<Email> bobEmails = new HashSet<Email>();    
        Email bobEmail1 = new Email("bob1"); 
        bobEmails.add(bobEmail1);        
        newPerson = new Person("Bob", bobEmails);
        repository.insert(newPerson);	
        
        System.out.println(repository.getById(2));
        
        System.out.println(repository.getAll());
        
        System.out.println("This is Bob: " + repository.getByName("Bob"));
        
	}
}
