package de.jonashill01.FitnessApp.personal.information.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person createPerson(String bodyStatsId, String dailyNutritionId, String sportActivity, String goalId, String name) {
       return personRepository.insert(new Person(bodyStatsId, dailyNutritionId, sportActivity, goalId, name));
    }
}
