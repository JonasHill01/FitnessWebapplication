package de.jonashill01.FitnessApp.personal.information;

import de.jonashill01.FitnessApp.personal.information.body_statistics.BodyStatistics;
import de.jonashill01.FitnessApp.personal.information.body_statistics.BodyStatisticsService;
import de.jonashill01.FitnessApp.personal.information.person.Person;
import de.jonashill01.FitnessApp.personal.information.person.PersonService;
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

        BodyStatistics bodyStatistics = bodyStatisticsService.createBodyStatistics(request.getWeight(), request.getHeight());
        Person newPerson = personService.createPerson(bodyStatistics.getObjId(), null, null, null, request.getSportActivity());

        return new ResponseEntity<>(newPerson, HttpStatus.CREATED);
    }

    private boolean isValidRequest(PersonalInformationRequest request) {
        int sportActivity = request.getSportActivity();
        int weight = request.getWeight();
        int height = request.getHeight();
        String name = request.getName();

        return sportActivity > 0 && weight > 0 && height > 0 && name != null;
    }

}
