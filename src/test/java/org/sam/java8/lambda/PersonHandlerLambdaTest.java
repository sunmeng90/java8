package org.sam.java8.lambda;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PersonHandlerLambdaTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	public static List<Person> preparePerons() throws ParseException {
		List<Person> persons = new ArrayList<Person>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Person p1 = new Person("sunmeng1", sdf.parse("1990-12-31"), Person.Sex.MALE, "test1@126.com");
		Person p2 = new Person("sunmeng2", sdf.parse("1990-12-31"), Person.Sex.MALE, "test2@126.com");
		Person p3 = new Person("sunmeng3", sdf.parse("1990-12-31"), Person.Sex.MALE, "test3@126.com");
		Person p4 = new Person("sunmeng4", sdf.parse("1990-12-31"), Person.Sex.MALE, "test4@126.com");
		Person p5 = new Person("sunmeng5", sdf.parse("1990-12-31"), Person.Sex.MALE, "test5@126.com");
		Person p6 = new Person("sunmeng6", sdf.parse("1990-12-31"), Person.Sex.MALE, "test6@126.com");
		Person p7 = new Person("sunmeng7", sdf.parse("1990-12-31"), Person.Sex.MALE, "test7@126.com");
		Person p8 = new Person("sunmeng8", sdf.parse("1990-12-31"), Person.Sex.MALE, "test8@126.com");
		Person p9 = new Person("sunmeng9", sdf.parse("1990-12-31"), Person.Sex.MALE, "test9@126.com");
		Person p10 = new Person("sunmeng10", sdf.parse("1990-12-31"), Person.Sex.MALE, "test10@126.com");

		persons.add(p1);
		persons.add(p2);
		persons.add(p3);
		persons.add(p4);
		persons.add(p5);
		persons.add(p6);
		persons.add(p7);
		persons.add(p8);
		persons.add(p9);
		persons.add(p10);
		return persons;
	}

	@Test
	public void testGetPersonsOlderThan() throws Exception {
		List<Person> persons = preparePerons();
		assertThat(PersonHandlerLambda.getPersonsOlderThan(persons, 20).size(), equalTo(10));
		assertThat(PersonHandlerLambda.getPersonsOlderThan(persons, 1000).size(), equalTo(0));
	}

	// Cons: can not make sure the exception are thrown by the expected method,
	// if there are multiple calls that throws the same exception
	@Test(expected = Exception.class)
	public void testGetPersonsOlderThanWithException() throws Exception {
		// try to uncomment the following line, test case will success too
		// if (1 == 1) { throw new Exception();}
		PersonHandlerLambda.getPersonsOlderThan(new ArrayList<Person>(0), -20);
	}

	// Pro: will capture the exception throws by the following statement, and
	// compare the exception message
	@Test
	public void testGetPersonsOlderThanWithException2() throws Exception {
		// try to uncomment the following line, the test case will fail
		// if (1 == 1) { throw new Exception();}
		thrown.expect(Exception.class);
		thrown.expectMessage("Invalid Age");
		PersonHandlerLambda.getPersonsOlderThan(new ArrayList<Person>(0), -20);
	}

	@Test
	public void testGetPersonWithCondition() throws Exception {
		List<Person> persons = preparePerons();
		List actualResult = PersonHandlerLambda.getPersonWithCondition(persons, (Person person) -> person.getAge() > 20);
		assertThat(actualResult.size(), equalTo(10));
	}
}
