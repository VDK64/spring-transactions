package com.example.transactions;

import com.example.transactions.entities.Person;
import com.example.transactions.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Executable implements CommandLineRunner {

    private final PersonService personService;

    @Override
    public void run(String... args) throws Exception {
        Person person = new Person();
        person.setAge(30);
        person.setName("Alfred");
        personService.savePerson(person);
        System.out.println();
    }

}
