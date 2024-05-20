package com.example.demo.model;

import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Person {
    /*
     * Definisco il Tipo Gender tramite enum
     */
    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }
    
    //Dati della persona
    private UUID id;
    private String name;
    private String religion;
    private Gender gender;
    private Boolean masters_deg;

    //costruttore con parametri
    public Person (@JsonProperty("id") UUID id, 
                   @JsonProperty("name") String name,
                   @JsonProperty("gender") Gender gender,
                   @JsonProperty("religion") String religion,
                   @JsonProperty("masters_deg") Boolean masters_deg){
        this.id=id;
        this.name=name;
        this.religion=religion;
        this.gender=gender;
        this.masters_deg=masters_deg;
    }
    
    //getter
    public UUID getId() {
        return id;
    }
    public Optional<UUID>getMaybeId(){
        return Optional.ofNullable(id);
    }
    public String getName() {
        return name;
    }
    public Gender getGender() {
        return gender;
    }
    public String getReligion(){
        return religion;
    }
    public Boolean getmasters_deg() {
        return masters_deg;
    }
}
