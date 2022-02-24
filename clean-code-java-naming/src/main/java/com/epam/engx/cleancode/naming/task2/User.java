package com.epam.engx.cleancode.naming.task2;

import java.util.Arrays;
import java.util.Date;

public class User {

	protected boolean isAdmin = false;

	private String dateOfBirth;

	private String name;

	private User[] subordinates;

	private int rating;

	public User(String name, String dateOfBirth, User[] subordinates) {
		this.dateOfBirth = dateOfBirth;
		this.name = name;
		this.subordinates = subordinates;
	}

	@Override
	public String toString() {
		return "User [dateOfBirth=" + dateOfBirth + ", name=" + name + ", isAdmin=" + isAdmin + ", subordinates="
				+ Arrays.toString(subordinates) + ", rating=" + rating + "]";
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
}
