package de.jonashill01.FitnessApp.personal.information.person;

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
    private ObjectId bodyStatsId, dailyNutritionId, goalId;
    private String name;
    private int sportActivity;

    public Person(ObjectId bodyStatsId, ObjectId dailyNutritionId, ObjectId goalId, String name, int sportActivity) {
        this.bodyStatsId = bodyStatsId;
        this.dailyNutritionId = dailyNutritionId;
        this.goalId = goalId;
        this.name = name;
        this.sportActivity = sportActivity;
    }
}
