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

        Optional<DailyNutrition> newDailyNutritionPlanOpt = createDailyNutrition(newActivity, request);
        if(newDailyNutritionPlanOpt.isEmpty()) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        DailyNutrition newDailyNutritionPlan = newDailyNutritionPlanOpt.get();

        return new ResponseEntity<>(newDailyNutritionPlan, HttpStatus.CREATED);
    }

    private boolean isRequestValid(ActivityRequest request) {
        final int hoursADay = 24;

        ObjectId personId = request.getPersonId();
        Optional<Person> personFromRequest = personService.getPersonFromObjectId(personId);
        int sleep = request.getSleep();
        int sitting = request.getSitting();
        int walking = request.getWalking();
        int training = request.getTraining();
        Date timestamp = request.getTimestamp();

        return personId != null && personFromRequest.isPresent()
                && sleep + sitting + walking + training == hoursADay && timestamp != null;
    }

    private Activity createActivityFromRequest(ActivityRequest request) {
        return activityService.createActivity(request.getPersonId(), request.getSleep(), request.getSitting(), request.getWalking(), request.getTraining(), request.getTimestamp());
    }

    private Optional<DailyNutrition> createDailyNutrition(Activity activity, ActivityRequest request) {

        Optional<Person> personOpt = personService.getPersonFromObjectId(request.getPersonId());
        if(personOpt.isEmpty()) return Optional.empty();
        Person person = personOpt.get();

        Optional<BodyStatistics> bodyStatisticsOpt = bodyStatisticsService.getBodyStatisticFromObjectId(person.getBodyStatsId());
        if(bodyStatisticsOpt.isEmpty()) return Optional.empty();
        BodyStatistics bodyStatistics = bodyStatisticsOpt.get();

        return Optional.of(dailyNutritionService.createDailyNutrition(person, bodyStatistics, activity));
    }

}
