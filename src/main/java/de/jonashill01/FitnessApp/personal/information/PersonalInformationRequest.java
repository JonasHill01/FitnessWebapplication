package de.jonashill01.FitnessApp.personal.information;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PersonalInformationRequest {

    private int sportActivity, weight, height;
    private String name;


}
