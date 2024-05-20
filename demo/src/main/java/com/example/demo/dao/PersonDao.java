package com.example.demo.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.demo.model.Person;
import com.example.demo.model.Person.Gender;

public interface PersonDao {
    //metodo per inserire person con ID
    int insertPerson(UUID id, Person person);
    
    /*
     * Implementazione tramite type default, riconosce se nel Json payload è presente id
     */
    default int addPerson(Person person){
        Optional<UUID> id = person.getMaybeId();
        if(!id.isPresent()){
            return insertPerson(UUID.randomUUID(), person);
        }
        return insertPerson(person.getId(), person);
    }

    //funzione di ritorno della lista delle persone
    List<Person> getAllPeople();

    Optional<Person> selectPersonById(UUID id);
    Optional<List<Person>>selectPeopleByGender(Gender gender);

    //Delete person
    int deletePersonById(UUID id);
    //Update person by ID
    int updatePersonById(UUID id, Person update);
}

/* ***********  VECCHIA IMPLEMENTAZIONE NO ID BY CHOICE  ***********
//metodo per inserire persona con ID generato randomicamente
default int addPerson(Person person){
    UUID id = UUID.randomUUID();
    return insertPerson(id, person);
}*/