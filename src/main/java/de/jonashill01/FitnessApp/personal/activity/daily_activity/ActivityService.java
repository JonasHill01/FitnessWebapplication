package de.jonashill01.FitnessApp.personal.activity.daily_activity;

import de.jonashill01.FitnessApp.personal.activity.nutrition.DailyNutrition;
import de.jonashill01.FitnessApp.personal.information.body_statistics.BodyStatistics;
import de.jonashill01.FitnessApp.personal.information.person.Person;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ActivityService {

    private ActivityRepository activityRepository;

    public Activity createActivity(ObjectId personId, int sleep, int sitting, int walking, int training, Date timestamp) {
        Activity newActivity = new Activity(personId, sleep, sitting, walking, training, timestamp);
        return activityRepository.insert(newActivity);
    }

    public Optional<Activity> getActivityFromObjectId(ObjectId objId) {
        return activityRepository.findById(objId);
    }

}
