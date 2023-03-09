package com.example.transactions.services;

import com.example.transactions.entities.Person;
import com.example.transactions.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PersonService {

    private final Logger logger = LoggerFactory.getLogger(PersonService.class);

    private final PersonRepository personRepository;

    public void savePerson(Person person) {
        personRepository.save(person);
    }

    public Person findPersonById(long personId) {
        return personRepository.findById(personId).orElseThrow();
    }

    public void setDefaultAccountToPerson(long personId) {
        Person person = personRepository.findById(personId).orElseThrow();
        person.setAccount(100);
        personRepository.save(person);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Retryable(maxAttempts = 7, backoff = @Backoff(delay = 1, maxDelay = 2))
    public Person addToPersonAccountWithSerializableTransaction(long personId, int value, int delay) {
        logger.info("---------------Update attempt---------------");
        Person findedPerson = personRepository.findById(personId).orElseThrow();
        findedPerson.setAccount(findedPerson.getAccount() + value);
        if (delay != 0) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return personRepository.save(findedPerson);
    }

    @Transactional
    public Person addToPersonAccountWithDefaultTransaction(long personId, int value, int delay) {
        Person findedPerson = personRepository.findById(personId).orElseThrow();
        findedPerson.setAccount(findedPerson.getAccount() + value);
        if (delay != 0) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return personRepository.save(findedPerson);
    }

}
