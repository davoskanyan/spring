package com.example.demo.repositories;

import com.example.demo.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PersonRepository extends MongoRepository<Person, String> {
	Person findByName(String name);

	List<Person> findByAge(int age);
}
