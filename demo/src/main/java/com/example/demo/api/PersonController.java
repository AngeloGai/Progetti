package com.example.demo.api;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Person;
import com.example.demo.model.Person.Gender;
import com.example.demo.service.PersonService;

@RequestMapping("api/v1/person")
@RestController
public class PersonController {
    private final PersonService personService;
    
    //costruttore
    @Autowired
    public PersonController(PersonService personService){
        this.personService=personService;
    }

    /*
     * Metodo del controller per aggiungere una persona, 
     * utilizza il metodo addPerson di PersonService
     */
    @PostMapping
    public void addPerson(@RequestBody Person person){
        personService.addPerson(person);
    }

    /*
     * Ritorna la lista di tutte le persone, richiama metodo di PersonService
     */
    @GetMapping
    public List<Person> getAllPeople(){
        return personService.getAllPeople();
    }

    /*
     * Ritorna Optional la persona con l'ID cercato
     */
    @GetMapping (path = "/{id}")
    public Person getPersonById(@PathVariable("id") UUID id){
        return personService.findById(id)
                            .orElse(null);
    }

    /*
     * Ritorna Optional la lista di persone col genere cercato
     */
    @GetMapping (path = "/gender/{gender}")
    public List<Person> getPeopleByGender(@PathVariable("gender") Gender gender){
        return personService.findByGender(gender)
                            .orElse(null);
    }
    
    /*
     * Delete person by ID
     */
    @DeleteMapping (path = "/{id}")
    public void deletePersonById(@PathVariable("id") UUID id){
        personService.deletePersonById(id);
    }
    
    /*
     * Update person by ID
     */
    @PutMapping (path = "/{id}")
    public void updatePersonById(@PathVariable("id") UUID id, @RequestBody Person update){
        personService.updatePersonById(id, update);
    }
}
