package de.jonashill01.FitnessApp.personal.information.body_statistics;

import de.jonashill01.FitnessApp.Utils;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BodyStatisticsService {

    private BodyStatisticsRepository bodyStatisticsRepository;


    public BodyStatistics createBodyStatistics(int weight, int height) {
        double heightInMeters = (double) height / 100;
        BodyStatistics newBodyStats = new BodyStatistics(weight, height, calculateBMI(weight, heightInMeters));
        return bodyStatisticsRepository.insert(newBodyStats);
    }

    public Optional<BodyStatistics> getBodyStatisticFromObjectId(ObjectId objId) {
        return bodyStatisticsRepository.findById(objId);
    }

    private double calculateBMI(int weight, double height) {
        double bmi = weight / Math.pow(height, 2);
        return Utils.round(bmi, 2);
    }

}
