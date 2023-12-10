package de.jonashill01.FitnessApp.personal.information.body_statistics;

import de.jonashill01.FitnessApp.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BodyStatisticsService {

    @Autowired
    private BodyStatisticsRepository bodyStatisticsRepository;


    public BodyStatistics createBodyStatistics(int weight, int height) {
        double heightInMeters = (double) height / 100;
        BodyStatistics newBodyStats = new BodyStatistics(weight, height, calculateBMI(weight, heightInMeters));
        return bodyStatisticsRepository.insert(newBodyStats);
    }

    private double calculateBMI(int weight, double height) {
        double bmi = weight / Math.pow(height, 2);
        return Utils.round(bmi, 2);
    }

}
