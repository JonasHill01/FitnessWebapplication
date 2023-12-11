package de.jonashill01.FitnessApp.personal.information;

import de.jonashill01.FitnessApp.personal.information.body_statistics.BodyStatistics;
import de.jonashill01.FitnessApp.personal.information.body_statistics.BodyStatisticsService;
import de.jonashill01.FitnessApp.personal.information.person.Person;
import de.jonashill01.FitnessApp.personal.information.person.PersonService;
import org.bson.types.ObjectId;
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

    @Autowired
    private BodyStatisticsService bodyStatisticsService;

    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody PersonalInformationRequest request) {
        if(!isValidRequest(request)) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        BodyStatistics bodyStatistics = getBodyStatisticFromRequest(request);
        Person newPerson = getNewPerson(request, bodyStatistics);

        return new ResponseEntity<>(newPerson, HttpStatus.CREATED);
    }
    private boolean isValidRequest(PersonalInformationRequest request) {
        int weight = request.getWeight();
        int height = request.getHeight();
        int age = request.getAge();
        String name = request.getName();
        ObjectId goalId = request.getGoalId();

        return weight > 0 && height > 0 && age > 0 && name != null && goalId != null;
    }

    private BodyStatistics getBodyStatisticFromRequest(PersonalInformationRequest request) {
        return bodyStatisticsService.createBodyStatistics(request.getWeight(), request.getHeight());
    }

    private Person getNewPerson(PersonalInformationRequest request, BodyStatistics bodyStatistics) {
        return personService.createPerson(bodyStatistics.getObjId(), request.getGoalId(), request.getAge(), request.getName(), request.isMale());
    }

}
