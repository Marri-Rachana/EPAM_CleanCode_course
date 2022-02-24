package com.epam.engx.cleancode.naming.task3;

public class HarshadNumbers{

	public String printHarshadNumbers() {
		StringBuilder listOfHarshadNumbers = new StringBuilder();
		long sequenceLimit = 200;
		for (int number = 1; number <= sequenceLimit; number++) {
			if (number % getSumOfDigitsInNumber(number) == 0) {
				listOfHarshadNumbers.append(number).append("\n");
			}
		}
		return listOfHarshadNumbers.toString();
	}

	private int getSumOfDigitsInNumber(int number) {
		int sumOfDigits = 0;
		while (number != 0) {
            sumOfDigits += number % 10;
            number = number / 10;
        }
		return sumOfDigits;
	}

}
