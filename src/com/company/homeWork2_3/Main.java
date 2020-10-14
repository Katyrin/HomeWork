package com.company.homeWork2_3;

import java.util.*;

public class Main {
    static PhoneBook phoneBook;
    public static void main(String[] args) {
        // ex 1
        thirstMethod();

        // ex 2
        phoneBook = new PhoneBook();
        Person p1 = new Person("Иванов", 89009001111L, "89009001111@mail.ru");

        ArrayList<Long> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(89501112233L);
        phoneNumbers.add(89512223311L);
        ArrayList<String> emails = new ArrayList<>();
        emails.add("89501112233@mail.ru");
        emails.add("89512223311@gmail.com");
        Person p2 = new Person("Петров",phoneNumbers,emails);

        Person p3 = new Person("Сидоров",88002001010L,"88002001010@mail.ru");
        Person p4 = new Person("Сидоров",89997772233L,"89997772233@gmail.com");

        phoneBook.addPerson(p1);
        phoneBook.addPerson(p2);
        phoneBook.addPerson(p3);
        phoneBook.addPerson(p4);

        System.out.println(phoneBook.getSurnames());
        System.out.println(phoneBook.getPersons());

        System.out.println(phoneBook.getPhones("Сидоров"));
        System.out.println(phoneBook.getEmails("Петров"));

    }

    private static void thirstMethod() {
        String text = "создать текст то есть массив с набором слов то есть " +
                "создать массив а не текст хорошо буду создавать массив";
        String[] arrayStr = text.split(" ");
        System.out.println(Arrays.toString(arrayStr));
        HashSet<String> hashSet = new HashSet<>(Arrays.asList(arrayStr));
        System.out.println(hashSet);
        HashMap<String,Integer> hashMap = new HashMap<>();
        for (String str : arrayStr){
            int count = 0;
            for (String str2 : arrayStr){
                if (str.equals(str2))
                    count++;
            }
            hashMap.put(str,count);
        }
        System.out.println(hashMap);
    }
}
