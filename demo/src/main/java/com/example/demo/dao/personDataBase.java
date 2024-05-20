package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Person;
import com.example.demo.model.Person.Gender;

@Repository("PDB") // Srping riconosce questa come repository
public class personDataBase implements PersonDao{
    /* 
     * Semplice database composto da un Arraylist
     * Static assicura che per multiple istanze create della classe il database rimanga unico
     * private in maniera che sia accessibile solo da questa classe
    */
    ArrayList<Person> PeopleDataBase = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        // utilizzo un metodo ArrayList per aggiungere un NUOVO oggetto Person all'interno del DataBase
        PeopleDataBase.add(new Person(id, 
                                      person.getName(),       //get paramenters da person input
                                      person.getGender(), 
                                      person.getReligion(),
                                      person.getmasters_deg()));
        return 1;
    }

    @Override
    public List<Person> getAllPeople() {
        return PeopleDataBase;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        /*
         * use stream on PeopleDataBase, return first found
         */
        return PeopleDataBase.stream()
                             .filter(person -> person.getId().equals(id)) //compare person.id to input id
                             .findFirst();
    }

    @Override
    public Optional<List<Person>> selectPeopleByGender(Gender gender) {
        /*
        * use stream on PeopleDataBase, collect filtered elements into a list
        */
        List<Person> peopleList = PeopleDataBase.stream()
                                                .filter(person -> person.getGender().equals(gender))
                                                .collect(Collectors.toList());
        
        // Return an Optional containing the list if it's not empty
        return Optional.of(peopleList);
    }

    //Function to delete a person from it's ID
    @Override
    public int deletePersonById(UUID id) {
        // Call ricerca tramite id, storage in un personMaybe optional
        Optional<Person> personMaybe = selectPersonById(id);
        
        if (personMaybe.isEmpty()){
            return 0;                 //return 0 se non viene trovato tramite id
        }
        PeopleDataBase.remove(personMaybe.get()); //rimozione dal database, metodi javaUtil
        return 1;
    }

    //Function to update a person from it's ID
    /* 
    @Override
    public int updatePersonById(UUID id, Person update) {
        return selectPersonById(id)
                .map(person -> {
                    int indexP = PeopleDataBase.indexOf(person);                      //store dell'indice dell'ID
                    if (indexP >= 0){                                                 //se l'index esiste allora aggiorna con update
                        PeopleDataBase.set(indexP, new Person(id, update.getName(),
                                                                  update.getGender(),
                                                                  update.getReligion(),
                                                                  update.getmasters_deg()));
                        return 1;
                    } 
                    return 0;
                })
                .orElse(0);
    }
    */
    @Override
    public int updatePersonById(UUID id, Person update) {
        return selectPersonById(id)
                .map(person -> {
                    int indexP = PeopleDataBase.indexOf(person);                      //store dell'indice dell'ID
                    if (indexP >= 0){                                                 //se l'index esiste allora aggiorna con update
                        Person updatedPerson = new Person(id, update.getName(),
                                                        update.getGender(),
                                                        update.getReligion(),
                                                        update.getmasters_deg());
                        PeopleDataBase.set(indexP, updatedPerson);
                        return 1;
                    } 
                    return 0;
                })
                .orElse(0);
    }

}
