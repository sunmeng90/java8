package org.meng.java8.lambda;

import java.util.Calendar;
import java.util.Date;

/**
 * Person
 *
 */
public class Person {
	public enum Sex {
		MALE, FEMALE
	}

	private String name;
	private Date birthday;
	private Sex gender;
	private String emailAddress;

	public Person(String name, Date birthday, Sex gender, String emailAddress) {
		super();
		this.name = name;
		this.birthday = birthday;
		this.gender = gender;
		this.emailAddress = emailAddress;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Sex getGender() {
		return gender;
	}

	public void setGender(Sex gender) {
		this.gender = gender;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public int getAge() {
		Calendar today = Calendar.getInstance();
		Calendar birthday = Calendar.getInstance();
		birthday.setTime(this.getBirthday());
		int yearDiff = today.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
		return yearDiff;
	}

}
