package com.company.homeWork2_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PhoneBook {
    private HashMap<String,Person> phoneBookHM = new HashMap<>();

    public HashMap<String, Person> getPhoneBookHM() {
        return phoneBookHM;
    }

    public void addPerson(Person person){
        if (isExist(person)){
            Iterator<Map.Entry<String, Person>> iter = phoneBookHM.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, Person> entry = iter.next();
                if (entry.getKey().equals(person.getSurname())){
                    entry.getValue().addPhoneNumbers(person.getPhoneNumbers());
                    entry.getValue().addEmailAddresses(person.getEmailAddress());
                }
            }
        }else
            phoneBookHM.put(person.getSurname(), person);
    }

    private boolean isExist(Person person){
        Iterator<Map.Entry<String, Person>> iter = phoneBookHM.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Person> entry = iter.next();
            if (entry.getKey().equals(person.getSurname())){
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getSurnames(){
        ArrayList<String> persons = new ArrayList<>();
        Iterator<Map.Entry<String, Person>> iter = phoneBookHM.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Person> entry = iter.next();
            persons.add(entry.getKey());

        }
        return persons;
    }

    public HashMap<String,Person> getPersons(){
        return phoneBookHM;
    }

    public ArrayList<Long> getPhones(String name){
        return phoneBookHM.get(name).getPhoneNumbers();
    }

    public ArrayList<String> getEmails(String name){
        return phoneBookHM.get(name).getEmailAddress();
    }
}
