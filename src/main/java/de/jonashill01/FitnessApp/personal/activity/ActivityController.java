package de.jonashill01.FitnessApp.personal.activity;

import de.jonashill01.FitnessApp.personal.activity.daily_activity.Activity;
import de.jonashill01.FitnessApp.personal.activity.daily_activity.ActivityService;
import de.jonashill01.FitnessApp.personal.activity.nutrition.DailyNutrition;
import de.jonashill01.FitnessApp.personal.activity.nutrition.DailyNutritionService;
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

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/person/activity/add")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private DailyNutritionService dailyNutritionService;

    @Autowired
    private PersonService personService;

    @Autowired
    private BodyStatisticsService bodyStatisticsService;

    @PostMapping
    public ResponseEntity<DailyNutrition> createDailyNutritionPlan(@RequestBody ActivityRequest request) {
        if(!isRequestValid(request)) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        Activity newActivity = createActivityFromRequest(request);

        Optional<Person> personOpt = personService.getPersonFromObjectId(request.getPersonId());
        if(personOpt.isEmpty()) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        Person person = personOpt.get();

        Optional<BodyStatistics> bodyStatisticsOpt = bodyStatisticsService.getBodyStatisticFromObjectId(person.getBodyStatsId());
        if(bodyStatisticsOpt.isEmpty()) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        BodyStatistics bodyStatistics = bodyStatisticsOpt.get();

        DailyNutrition newDailyNutrition = createDailyNutrition(person, bodyStatistics, newActivity);
        System.out.println(newDailyNutrition);

        return new ResponseEntity<>(newDailyNutrition, HttpStatus.CREATED);
    }

    private boolean isRequestValid(ActivityRequest request) {
        final int hoursADay = 24;

        ObjectId personId = request.getPersonId();
        int sleep = request.getSleep();
        int sitting = request.getSitting();
        int walking = request.getWalking();
        int training = request.getTraining();
        Date timestamp = request.getTimestamp();

        return personId != null && sleep + sitting + walking + training == hoursADay && timestamp != null;
    }

    private Activity createActivityFromRequest(ActivityRequest request) {
        return activityService.createActivity(request.getSleep(), request.getSitting(), request.getWalking(), request.getTraining(), request.getTimestamp());
    }

    private DailyNutrition createDailyNutrition(Person person, BodyStatistics bodyStatistics, Activity activity) {
        return dailyNutritionService.createDailyNutrition(person, bodyStatistics, activity);
    }

}
