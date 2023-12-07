package com.danividalg.skillpillweb.database.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.danividalg.skillpillweb.database.model.Hello;

public interface HelloRepository extends MongoRepository<Hello, String> {
	@Query(value = "{ 'num' : ?0 }", fields = "{ '_id' : 1, 'num' : 1, 'text' : 1 }")
	List<Hello> findByNum(int num);

	long count();
}
