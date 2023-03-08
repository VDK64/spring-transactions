package com.example.transactions.services;

import com.example.transactions.entities.Person;
import com.example.transactions.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public void savePerson(Person person) {
        personRepository.save(person);
    }

    public void deletePerson(long personId) {
        personRepository.deleteById(personId);
    }

    public Person findPersonById(long personId) {
        return personRepository.findById(personId).orElseThrow();
    }

    public void updatePerson(Person person) {
        personRepository.save(person);
    }

}
