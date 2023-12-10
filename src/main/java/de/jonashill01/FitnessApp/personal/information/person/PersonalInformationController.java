package de.jonashill01.FitnessApp.personal.information.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/person/information/add")
public class PersonalInformationController {

    @Autowired
    private PersonService personService;

    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Map<String, String> payload) {
        String bodyStatsId = payload.get("bodyStatsId");
        String dailyNutritionId = payload.get("dailyNutritionId");
        String goalId = payload.get("goalId");
        String sportActivity = payload.get("sportActivity");
        String name = payload.get("name");

        if(bodyStatsId == null || dailyNutritionId ==  null || goalId == null || sportActivity == null || name == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        else {
            Person newPerson = personService.createPerson(bodyStatsId, dailyNutritionId, sportActivity, goalId, name);
            return new ResponseEntity<>(newPerson, HttpStatus.CREATED);
        }
    }

}
