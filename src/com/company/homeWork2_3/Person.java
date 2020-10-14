package com.company.homeWork2_3;

import java.util.ArrayList;

public class Person {
    private String surname;
    private ArrayList<Long> phoneNumbers = new ArrayList<>();
    private ArrayList<String> emailAddresses = new ArrayList<>();

    public void addPhoneNumbers(ArrayList<Long> phoneNumbers) {
        this.phoneNumbers.addAll(phoneNumbers);
    }

    public void addEmailAddresses(ArrayList<String> emailAddresses) {
        this.emailAddresses.addAll(emailAddresses);
    }

    public Person(String surname, ArrayList<Long> phoneNumbers, ArrayList<String> emailAddresses) {
        this.surname = surname;
        this.phoneNumbers.addAll(phoneNumbers);
        this.emailAddresses.addAll(emailAddresses);
    }

    public Person(String surname, Long phoneNumber, String emailAddress) {
        this.surname = surname;
        phoneNumbers.add(phoneNumber);
        emailAddresses.add(emailAddress);
    }

    public String getSurname() {
        return surname;
    }

    public ArrayList<Long> getPhoneNumbers() {
        return phoneNumbers;
    }

    public ArrayList<String> getEmailAddress() {
        return emailAddresses;
    }

    @Override
    public String toString() {
        return " phoneNumbers: " + phoneNumbers +
                ", emailAddresses:" + emailAddresses;
    }
}
