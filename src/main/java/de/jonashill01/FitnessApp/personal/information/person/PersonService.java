package de.jonashill01.FitnessApp.personal.information.person;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person createPerson(ObjectId bodyStatsId, ObjectId dailyNutritionId, ObjectId goalId, String name, int sportActivity) {
       return personRepository.insert(new Person(bodyStatsId, dailyNutritionId, goalId, name, sportActivity));
    }
}
