package de.jonashill01.FitnessApp.personal.activity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.Date;

@AllArgsConstructor
@Data
public class ActivityRequest {

    private ObjectId personId;
    private int sleep, sitting, walking, training;
    private Date timestamp;

}
