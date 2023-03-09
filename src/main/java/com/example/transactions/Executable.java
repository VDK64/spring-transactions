package com.example.transactions;

import com.example.transactions.entities.Person;
import com.example.transactions.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class Executable implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(Executable.class);

    private final PersonService personService;

    @Override
    public void run(String... args) throws Exception {
        doIndent();
        createPerson("Alfred", 100);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(() -> addToPersonAccountWithDefaultTransaction(1L, 1, 5000));
        Thread.sleep(1000);
        executorService.execute(() -> addToPersonAccountWithDefaultTransaction(1L, 7, 0));
        Thread.sleep(12000);
        logger.info("Person data in database: {}", personService.findPersonById(1L));
        personService.setDefaultAccountToPerson(1L);

        doIndent();

        executorService.execute(() -> addToPersonAccountWithSerializableTransaction(1L, 1, 5000));
        Thread.sleep(1000);
        executorService.execute(() -> addToPersonAccountWithSerializableTransaction(1L, 7, 0));
        Thread.sleep(12000);
        logger.info("Person data in database: {}", personService.findPersonById(1L));
        executorService.shutdown();
    }

    private void doIndent() {
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }

    private void createPerson(String name, int account) {
        Person person = new Person();
        person.setAccount(account);
        person.setName(name);
        personService.savePerson(person);
    }

    private void addToPersonAccountWithSerializableTransaction(long personId, int value, int delay) {
        logger.info("Start transaction by incrementing person account on {}", value);
        Person savedPerson = personService.addToPersonAccountWithSerializableTransaction(personId, value, delay);
        logger.info("End transaction by incrementing person account on {} {}", value, savedPerson);
    }

    private void addToPersonAccountWithDefaultTransaction(long personId, int value, int delay) {
        logger.info("Start transaction by incrementing person account on {}", value);
        Person savedPerson = personService.addToPersonAccountWithDefaultTransaction(personId, value, delay);
        logger.info("End transaction by incrementing person account on {} {}", value, savedPerson);
    }

}
