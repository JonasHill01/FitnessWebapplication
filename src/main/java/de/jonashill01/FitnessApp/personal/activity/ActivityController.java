package de.jonashill01.FitnessApp.personal.activity;

import de.jonashill01.FitnessApp.personal.activity.daily_activity.Activity;
import de.jonashill01.FitnessApp.personal.activity.daily_activity.ActivityService;
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

    @PostMapping
    public ResponseEntity<DailyNutrition> createDailyNutritionPlan(@RequestBody ActivityRequest request) {
        if(!isRequestValid(request)) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        Activity newActivity = getActivityFromRequest(request);
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

    private Activity getActivityFromRequest(ActivityRequest request) {
        return activityService.createActivity(request.getSleep(), request.getSitting(), request.getWalking(), request.getTraining(), request.getTimestamp());
    }

}
