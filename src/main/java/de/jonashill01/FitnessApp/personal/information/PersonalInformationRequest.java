package de.jonashill01.FitnessApp.personal.information;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;

@AllArgsConstructor
@Data
public class PersonalInformationRequest {

    private int age, weight, height;
    private String name;
    private boolean isMale;
    private ObjectId goalId;


}
