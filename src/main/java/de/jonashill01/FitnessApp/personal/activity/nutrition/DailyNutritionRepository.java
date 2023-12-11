package de.jonashill01.FitnessApp.personal.activity.nutrition;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyNutritionRepository extends MongoRepository<DailyNutrition, ObjectId> {
}
