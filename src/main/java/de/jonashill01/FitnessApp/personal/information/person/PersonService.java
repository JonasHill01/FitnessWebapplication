package de.jonashill01.FitnessApp.personal.information.person;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person createPerson(ObjectId bodyStatsId, ObjectId goalId, int age, String name, boolean isMale) {
       return personRepository.insert(new Person(bodyStatsId, goalId, age, name, isMale));
    }
}
