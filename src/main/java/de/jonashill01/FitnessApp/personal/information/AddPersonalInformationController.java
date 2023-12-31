package de.jonashill01.FitnessApp.personal.information;

import de.jonashill01.FitnessApp.personal.information.body_statistics.BodyStatistics;
import de.jonashill01.FitnessApp.personal.information.body_statistics.BodyStatisticsService;
import de.jonashill01.FitnessApp.personal.information.person.Person;
import de.jonashill01.FitnessApp.personal.information.person.PersonService;
import de.jonashill01.FitnessApp.personal.information.request.AddPersonalInformationRequest;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/person/information/add")
@AllArgsConstructor
public class AddPersonalInformationController {

    private final PersonService personService;

    private final BodyStatisticsService bodyStatisticsService;

    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody AddPersonalInformationRequest request) {
        if(!isValidRequest(request)) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        BodyStatistics bodyStatistics = getBodyStatisticFromRequest(request);
        Person newPerson = getNewPerson(request, bodyStatistics);

        return new ResponseEntity<>(newPerson, HttpStatus.CREATED);
    }
    private boolean isValidRequest(AddPersonalInformationRequest request) {
        int weight = request.getWeight();
        int height = request.getHeight();
        int age = request.getAge();
        String name = request.getName();
        ObjectId goalId = request.getGoalId();

        return weight > 0 && height > 0 && age > 0 && name != null && goalId != null;
    }

    private BodyStatistics getBodyStatisticFromRequest(AddPersonalInformationRequest request) {
        return bodyStatisticsService.createBodyStatistics(request.getWeight(), request.getHeight());
    }

    private Person getNewPerson(AddPersonalInformationRequest request, BodyStatistics bodyStatistics) {
        return personService.createPerson(bodyStatistics.getObjId(), request.getGoalId(), request.getAge(), request.getName(), request.isMale());
    }

}
