package de.jonashill01.FitnessApp.personal.activity.daily_activity;

import de.jonashill01.FitnessApp.personal.activity.nutrition.DailyNutrition;
import de.jonashill01.FitnessApp.personal.information.body_statistics.BodyStatistics;
import de.jonashill01.FitnessApp.personal.information.person.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public Activity createActivity(int sleep, int sitting, int walking, int training, Date timestamp) {
        Activity newActivity = new Activity(sleep, sitting, walking, training, timestamp);
        return activityRepository.insert(newActivity);
    }
}
