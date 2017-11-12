package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Person implements Serializable {
	private static final long serialVersionUID = 7272380872390975378L;

	@Id
	public String id;

	public final String name;
	public final int age;
	public final int height;

	@JsonCreator
	public Person(@JsonProperty("name") String name, @JsonProperty("age") int age,
	              @JsonProperty("height") Integer height) {
		this.name = name;
		this.age = age;
		this.height = height;
	}

	@Override
	public String toString() {
		return String.format(
				"Person[id=%s, name='%s', age='%s', height='%s']",
				id, name, height, height);
	}
}
