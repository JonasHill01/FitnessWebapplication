package de.jonashill01.FitnessApp.personal.activity.nutrition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "daily_nutrition")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyNutrition {

    @Id
    private ObjectId objId;
    private ObjectId dailyActivityId;
    private Date timestamp;
    private int calories, proteins, carbs, fats;

    public DailyNutrition(ObjectId dailyActivityId, Date timestamp, int calories, int proteins, int carbs, int fats) {
        this.dailyActivityId = dailyActivityId;
        this.timestamp = timestamp;
        this.calories = calories;
        this.proteins = proteins;
        this.carbs = carbs;
        this.fats = fats;
    }
}
