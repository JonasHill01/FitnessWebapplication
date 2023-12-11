package de.jonashill01.FitnessApp.personal.activity.nutrition;

import de.jonashill01.FitnessApp.personal.activity.daily_activity.Activity;
import de.jonashill01.FitnessApp.personal.information.body_statistics.BodyStatistics;
import de.jonashill01.FitnessApp.personal.information.person.Person;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DailyNutritionService {

    private DailyNutritionRepository dailyNutritionRepository;

    public DailyNutrition createDailyNutrition(Person person, BodyStatistics bodyStatistics, Activity activity) {
        int baseCalories = getBaseCalories(person, bodyStatistics);
        int performanceCalories = getPerformanceCalories(activity, baseCalories);
        int sumCalories = baseCalories + performanceCalories;
        int proteins = calculateProteins(activity, bodyStatistics);
        int fats = calculateFats(activity, bodyStatistics);
        int carbs = calculateCarbs(sumCalories, proteins, fats);

        DailyNutrition newDailyNutrition = new DailyNutrition(activity.getObjId(), activity.getTimestamp(), sumCalories, proteins, carbs, fats);
        return dailyNutritionRepository.insert(newDailyNutrition);
    }

    public Optional<DailyNutrition> getDailyNutritionFromObjectId(ObjectId objId) {
        return dailyNutritionRepository.findById(objId);
    }

    /**
     * Get the base Nutrition value.
     * */
    private int getBaseCalories(Person person, BodyStatistics bodyStatistics) {
        if(person.isMale()) return getBaseCaloriesMen(person, bodyStatistics);
        else return getBaseCaloriesWomen(person, bodyStatistics);
    }

    /**
     * Get the base Nutrition value for a Men.
     * */
    private int getBaseCaloriesMen(Person person, BodyStatistics bodyStatistics) {
        double baseNutrition = calculateBaseCalories(66.47, 13.7, 5, 6.8, bodyStatistics, person);
        return (int) Math.ceil(baseNutrition);
    }

    /**
     * Get the base Nutrition value for a Women.
     * */
    private int getBaseCaloriesWomen(Person person, BodyStatistics bodyStatistics) {
        double baseNutrition = calculateBaseCalories(655.1, 9.6, 1.8, 4.7, bodyStatistics, person);
        return (int) Math.ceil(baseNutrition);
    }

    /**
     * Calculate the base Nutrition value.
     * */
    private double calculateBaseCalories(double genderSpecificValue, double weightMultiplier, double heightMultiplier, double ageMultiplier, BodyStatistics bodyStatistics, Person person) {
        return genderSpecificValue + (weightMultiplier * bodyStatistics.getWeight()) + (heightMultiplier * bodyStatistics.getHeight()) - (ageMultiplier * person.getAge());
    }

    /**
     * Get the performance Nutrition value.
     * */
    private int getPerformanceCalories(Activity activity, int baseNutrition) {
        double palFactor = calculatePalFactor(activity);
        double result = (baseNutrition * palFactor) - baseNutrition;
        return (int) Math.ceil(result);
    }

    /**
     * Get the Pal factor.
     * */
    private double calculatePalFactor(Activity activity) {
        final int hoursADay = 24;
        double palValueSleeping = 0.95, palValueSitting = 1.1, palValueWalking = 1.8, palValueTraining = 2.4;

        double sleepingVal = palValueSleeping * activity.getSleep();
        double sittingVal = palValueSitting * activity.getSitting();
        double walkingVal = palValueWalking * activity.getWalking();
        double trainingVal = palValueTraining * activity.getTraining();

        return (sleepingVal + sittingVal + walkingVal + trainingVal) / hoursADay;
    }

    private int calculateProteins(Activity activity, BodyStatistics bodyStatistics) {
        double baseNeededValPerKiloGram = calculateBaseNeededValPerKiloGramWeight(activity, 2.2);
        double result = calculateNeededNutrition(baseNeededValPerKiloGram, bodyStatistics.getWeight());
        return (int) Math.ceil(result);
    }

    private int calculateFats(Activity activity, BodyStatistics bodyStatistics) {
        double baseNeededValPerKiloGram = calculateBaseNeededValPerKiloGramWeight(activity, 0.4);
        double result = calculateNeededNutrition(baseNeededValPerKiloGram, bodyStatistics.getWeight());
        return (int) Math.ceil(result);
    }

        private double calculateBaseNeededValPerKiloGramWeight(Activity activity, double baseVal) {
        return baseVal + calculateBaseKiloGramIncrease(activity);
    }
    private double calculateBaseKiloGramIncrease(Activity activity) {
        double palFactor = calculatePalFactor(activity);
        if(palFactor <= 1) return 0;
        else if(palFactor <= 1.15) return 0.1;
        else if(palFactor <= 1.2) return 0.2;
        else if(palFactor <= 1.3) return 0.3;
        else if(palFactor <= 1.4) return 0.4;
        else  return 0.5;
    }

    private double calculateNeededNutrition(double baseValPerKiloGramWeight, int weight) {
        return baseValPerKiloGramWeight * weight;
    }

    private int calculateCarbs(int calories, int proteinsInGram, int fatsInGram) {
        int caloriesFromFatsAndProtein = calculateCaloriesForProtein(proteinsInGram) + calculateCaloriesForFats(fatsInGram);
        int leftOverCalories = calories - caloriesFromFatsAndProtein;
        final double caloriesToCarbsDivider = 4.1;
        return (int) Math.ceil(leftOverCalories / caloriesToCarbsDivider);
    }

    private int calculateCaloriesForProtein(int proteinsInGram) {
        final double proteinsInCaloriesMultiplier = 4.1;
        return (int) Math.ceil(proteinsInGram *  proteinsInCaloriesMultiplier);
    }

    private int calculateCaloriesForFats(int fatsInGram) {
        final double fatsInCaloriesMultiplier = 9.3;
        return (int) Math.ceil(fatsInGram *  fatsInCaloriesMultiplier);
    }

}
