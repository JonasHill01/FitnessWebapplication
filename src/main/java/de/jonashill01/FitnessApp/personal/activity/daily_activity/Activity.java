package de.jonashill01.FitnessApp.personal.activity.daily_activity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "daily-activity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity {

    @Id
    private ObjectId objId;
    private ObjectId personId;
    private int sleep, sitting, walking, training;
    private Date timestamp;

    public Activity(ObjectId personId, int sleep, int sitting, int walking, int training, Date timestamp) {
        this.personId = personId;
        this.sleep = sleep;
        this.sitting = sitting;
        this.walking = walking;
        this.training = training;
        this.timestamp = timestamp;
    }
}
