package de.jonashill01.FitnessApp.personal.information;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document( collection = "person")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    private ObjectId objId;
    private String bodyStatsId, dailyNutritionId, sportActivity, goalId, name;

    public Person(String bodyStatsId, String dailyNutritionId, String sportActivity, String goalId, String name) {
        this.bodyStatsId = bodyStatsId;
        this.dailyNutritionId = dailyNutritionId;
        this.sportActivity = sportActivity;
        this.goalId = goalId;
        this.name = name;
    }
}
