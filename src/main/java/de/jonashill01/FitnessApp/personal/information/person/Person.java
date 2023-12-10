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
    private ObjectId bodyStatsId, goalId;
    private int age;
    private String name;
    private boolean isMale;

    public Person(ObjectId bodyStatsId, ObjectId goalId, int age, String name, boolean isMale) {
        this.bodyStatsId = bodyStatsId;
        this.goalId = goalId;
        this.age = age;
        this.name = name;
        this.isMale = isMale;
    }
}
