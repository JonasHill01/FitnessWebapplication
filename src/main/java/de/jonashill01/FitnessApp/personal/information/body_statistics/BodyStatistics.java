package de.jonashill01.FitnessApp.personal.information.body_statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "body-stats")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BodyStatistics {

    @Id
    private ObjectId objId;
    private int weight, height;
    private double bmi;

    public BodyStatistics(int weight, int height, double bmi) {
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
    }
}
