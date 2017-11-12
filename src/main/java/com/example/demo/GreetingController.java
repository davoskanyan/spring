package com.example.demo;

import com.example.demo.messaging.MessagePublisher;
import com.example.demo.model.Person;
import com.example.demo.repositories.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GreetingController {

	@Autowired
	private RedisOperations<String, Object> redisTemplate;

	@Autowired
	private MessagePublisher messagePublisher;

	@Autowired
	private MessageListenerAdapter messageListener;

	@Autowired
	private PersonRepository repository;

	HashMapper<Object, byte[], byte[]> mapper = new ObjectHashMapper();

	@RequestMapping("/greeting")
	@ResponseBody
	public String greeting(@RequestParam String name, @RequestParam int age, @RequestParam Integer height) {
		redisTemplate.opsForValue().set("person", new Person(name, age, height));
		return "Success";
	}

	@RequestMapping("/hello")
	@ResponseBody
	public Person hello() {
		return (Person) redisTemplate.opsForValue().get("person");
	}

	@RequestMapping("/publish")
	@ResponseBody
	public String publish() throws JsonProcessingException {
		Person person = (Person) redisTemplate.opsForValue().get("person");

		messagePublisher.publish(person);

		return "Success";
	}

	@RequestMapping("/mongo")
	@ResponseBody
	public String mongo() {
		repository.deleteAll();

		// save a couple of customers
		repository.save(new Person("Alice", 15, 165));
		repository.save(new Person("Bob", 35, 180));

		// fetch all persons
		System.out.println("Persons found with findAll():");
		System.out.println("-------------------------------");
		for (Person person : repository.findAll()) {
			System.out.println(person);
		}
		System.out.println();

		// fetch an individual person
		System.out.println("Person found with findByFirstName('Alice'):");
		System.out.println("--------------------------------");
		System.out.println(repository.findByName("Alice"));

		System.out.println("Customers found with findByAge(15):");
		System.out.println("--------------------------------");
		for (Person person : repository.findByAge(35)) {
			System.out.println(person);
		}

		return "Success";
	}
}