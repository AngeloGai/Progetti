package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.dao.PersonDao;
import com.example.demo.model.Person;
import com.example.demo.model.Person.Gender;

@Service
public class PersonService {
    private PersonDao personDao;

    //costruttore
    @Autowired
    public PersonService(@Qualifier("postgres") PersonDao personDao){
        this.personDao=personDao;
    }
    
    /*
     * Service che richiama l'interfaccia PersonDao, nella quale è implementato 
     * di default il metodo insertPerson
     */
    public int addPerson(Person person){
        return personDao.addPerson(person);
    }

    /*
     * Utilizza metodi definiti nell'interfaccia PersonDao e implementati nel PersonDataBase 
     */
    public List<Person> getAllPeople(){
        return personDao.getAllPeople();
    }
    public Optional<Person> findById(UUID id){
        return personDao.selectPersonById(id);
    }
    public Optional<List<Person>> findByGender(Gender gender){
        return personDao.selectPeopleByGender(gender);
    }
    public int deletePersonById(UUID id){
        return personDao.deletePersonById(id);
    }
    public int updatePersonById(UUID id, Person person){
        return personDao.updatePersonById(id, person);
    }
}
