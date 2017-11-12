package com.example.demo.messaging;

import com.example.demo.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RedisMessageSubscriber implements MessageListener {

	public static List<String> messageList = new ArrayList<>();

	public void onMessage(Message message, byte[] pattern) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Person person = mapper.readValue(message.toString(), Person.class);
			messageList.add(person.toString());
			System.out.println("Message received: " + person.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
