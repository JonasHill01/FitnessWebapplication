package de.jonashill01.FitnessApp.personal.information.body_statistics;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyStatisticsRepository extends MongoRepository<BodyStatistics, ObjectId> {
}
