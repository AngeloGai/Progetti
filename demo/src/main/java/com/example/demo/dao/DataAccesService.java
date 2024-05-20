package com.example.demo.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Person;
import com.example.demo.model.Person.Gender;


@Repository("postgres")
public class DataAccesService implements PersonDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DataAccesService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }


    /*
    * Utilizzo query di JdbcTemplate per inserire la persona nel DB postgres tramite Json payload
    */
    @Override
    public int insertPerson(UUID id, Person person) {
        final String queryString = "INSERT INTO person (id, name, gender, religion, masters_deg) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(queryString, 
            id, 
            person.getName(), 
            person.getGender().ordinal() + 1, // Converti l'enumerazione Gender in un valore int
            person.getReligion(), 
            person.getmasters_deg()
        );
        return 1;
    }

    /* 
     * Utilizzo di JdbcTemplate per effettuare la query e ritornarla come lista tramite 
     * doppio return in linea;
     * jdbcTemplate(queryString, rowMapper)
    */
    @Override
    public List<Person> getAllPeople() {

        final String queryString = "SELECT * FROM person";
        return jdbcTemplate.query(queryString, (resultSet,i) -> {
            UUID id = (UUID) resultSet.getObject("id");
            String name = resultSet.getString("name");
            Gender gender = switch (resultSet.getInt("gender")) {  //Switch case per importare gender da database
                case 1 -> Gender.MALE;
                case 2 -> Gender.FEMALE;
                case 3 -> Gender.OTHER;
                default -> Gender.OTHER;
            };
            String religion = resultSet.getString("religion");
            Boolean masters_deg = resultSet.getBoolean("masters_deg");

            return new Person(id, name, gender, religion, masters_deg);
        });
        
    }

    /* 
     * Ricerca per id, metodo revisionato in jdbcTemplate(queryString, rowMapper, newOBJ);
    */
    @Override
    public Optional<Person> selectPersonById(UUID id) {
        final String queryString = "SELECT * FROM person WHERE  id = ?";
        Person person = jdbcTemplate.queryForObject(
            queryString,
            (resultSet, i) -> {
                UUID id1 = (UUID) resultSet.getObject("id");
                String name = resultSet.getString("name");
                Gender gender = switch (resultSet.getInt("gender")) {  //Switch case per importare gender da database
                    case 1 -> Gender.MALE;
                    case 2 -> Gender.FEMALE;
                    case 3 -> Gender.OTHER;
                    default -> Gender.MALE;
                };
                String religion = resultSet.getString("religion");
                Boolean masters_deg = resultSet.getBoolean("masters_deg");
    
                return new Person(id1, name, gender, religion, masters_deg);
            },
            new Object[]{id});
        return Optional.ofNullable(person);
    }

    @Override
    public Optional<List<Person>> selectPeopleByGender(Gender gender) {

        final String queryString = "SELECT * FROM person WHERE  gender = ?";

        List<Person> people = jdbcTemplate.query(queryString, preparedStatement -> {
            preparedStatement.setInt(1, gender.ordinal() + 1);  // Imposta il valore del genere nel PreparedStatement
        }, (resultSet,i) -> {

            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            Gender genderone = switch (resultSet.getInt("gender")) {  //Switch case per importare gender da database
                case 1 -> Gender.MALE;
                case 2 -> Gender.FEMALE;
                case 3 -> Gender.OTHER;
                default -> Gender.OTHER;
            };
            String religion = resultSet.getString("religion");
            Boolean masters_deg = resultSet.getBoolean("masters_deg");

            return new Person(id, name, genderone, religion, masters_deg);
        });

        return Optional.ofNullable(people.isEmpty() ? null : people);
        
    }

    /*
     * Controlla che la persona esista, se la query ha esito positivo allora procede alla query di eliminazione,
     * return 0 altrimenti
    */
    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> personMaybe= selectPersonById(id);
        if (personMaybe.isPresent()){
            final String queryString = "DELETE FROM person WHERE id = ?";
        jdbcTemplate.update(queryString, id);
        return 1;
        }
        return 0;
    } 

    /*  ***********  Versione semplificata senza codice di errore  ***********
    @Override
    public int deletePersonById(UUID id) {
        final String queryString = "DELETE FROM person WHERE id = ?";
        jdbcTemplate.update(queryString, id);
        return 1;
    } */

    /*
     * La funzione esegue la query di ricerca per id in modo da capire se l'elemento to update esiste,
     * in caso positivo esegue la query di update, altrimenti return 0;
     */
    @Override
    public int updatePersonById(UUID id, Person update) {
        Optional<Person> personMaybe= selectPersonById(id);
        if (personMaybe.isPresent()){
            final String queryString = "UPDATE person SET name = ?, gender = ?, religion = ?, masters_deg = ? WHERE id = ?";
            jdbcTemplate.update(queryString, 
                update.getName(), 
                update.getGender().ordinal() + 1, // Converti l'enumerazione Gender in un valore int
                update.getReligion(), 
                update.getmasters_deg(),
                id
            );
        return 1;
        }
        return 0;
    }
    
}
