package org.meng.java8.lambda;

import java.util.ArrayList;
import java.util.List;

public class PersonHandlerLambda {

	public static List<Person> getPersonsOlderThan(List<Person> persons, int age) throws Exception {
		if (age < 0) {
			throw new Exception("Invalid Age");
		}
		List<Person> result = new ArrayList<Person>();
		persons.forEach((person) -> {
			if (age <= person.getAge()) {
				result.add(person);
			}
		});
		return result;

	}

	public static List<Person> getPersonsWithinAgeRange(List<Person> persons, int minAge, int maxAge) throws Exception {
		if (minAge < 0 || maxAge < minAge) {
			throw new Exception("Invalid age, minAge is large than maxAge");
		}
		List<Person> result = new ArrayList<Person>();
		for (Person p : persons) {
			if (maxAge >= p.getAge() && minAge <= p.getAge()) {
				result.add(p);
			}
		}
		return result;
	}

	/**
	 * functional interface: interface with one and only one method
	 * @author meng_
	 *
	 */
	interface PersonCondition {
		public boolean check(Person person);
	}

	// more general example
	public static List<Person> getPersonWithCondition(List<Person> persons, PersonCondition condition) {
		List<Person> result = new ArrayList<Person>();
		persons.forEach((person) -> {
			if (condition.check(person)) {
				result.add(person);
			}
		});
		return result;
	}

}
