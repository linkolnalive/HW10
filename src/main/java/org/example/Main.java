package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


public class Main {
    // task 1
    public static void checkPhoneNumbersFromFile() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("file.txt"));
            String line = reader.readLine();
            while (line != null) {
                if (validNumber(line)) {
                    System.out.println(line);
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean validNumber(String line) {
        char[] lineArray = line.toCharArray();
        if (lineArray[0] == '(' && line.length() == 14 && lineArray[4] == ')' && lineArray[5] == ' ' && lineArray[9] == '-') {
            return isDigit(lineArray[1]) && isDigit(lineArray[2]) && isDigit(lineArray[3]) && isDigit(lineArray[6]) && isDigit(lineArray[7]) && isDigit(lineArray[8]) && isDigit(lineArray[10]) && isDigit(lineArray[11]) && isDigit(lineArray[12]) && isDigit(lineArray[13]);
        } else if (lineArray[3] == '-' && line.length() == 12 && lineArray[7] == '-') {
            return isDigit(lineArray[0]) && isDigit(lineArray[1]) && isDigit(lineArray[2]) && isDigit(lineArray[4]) && isDigit(lineArray[5]) && isDigit(lineArray[6]) && isDigit(lineArray[8]) && isDigit(lineArray[9]) && isDigit(lineArray[10]) && isDigit(lineArray[11]);
        }
        return false;
    }
    public static boolean isDigit(char c) {
        return "0123456789".contains(Character.toString(c));
    }

    // task 2
    static class User {
        HashMap<String, Object> fields = new HashMap<String, Object>();
    }
    public static void generateUserJsonFromFile() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("file.txt"));
            String[] fieldNames = reader.readLine().split(" ");

            String line = reader.readLine();
            ArrayList<User> users = new ArrayList<User>();
            while (line != null) {
                if (line.split(" ").length != fieldNames.length) {
                    throw new IOException("Wrong amount of field values");
                }
                User user = new User();
                for (int i = 0; i < line.split(" ").length; i++) {
                    user.fields.put(fieldNames[i], line.split(" ")[i]);
                }
                users.add(user);
                line = reader.readLine();
            }
            reader.close();

            JSONArray outerJA = new JSONArray();
            for (User user : users) {
                JSONObject innerJO = new JSONObject(user.fields);
//                for (String fieldName: fieldNames) {
//                    innerJO.put(fieldName, user.fields.get(fieldName));
//                }
                outerJA.add(innerJO);
            }
            String res = outerJA.toJSONString();

            FileWriter file = new FileWriter("/Users/techart/Desktop/HW10/user.json");
            file.write(res);
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // task 3
    public static void countWordsFromFile() {
        LinkedList<ArrayList<String>> res = new LinkedList<ArrayList<String>>();

        BufferedReader reader;
        StringBuilder text = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader("file.txt"));
            String line = reader.readLine();
            while (line != null) {
                text.append(line).append(' ');
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String currWord: text.toString().split(" +")) {
            int count = 0;
            for(String word: text.toString().split(" +")) {
                if (word.equals(currWord)) {
                    count++;
                }
            }
            while (count > res.size()) {
                res.add(new ArrayList<String>());
            }
            boolean found = false;
            for (String word: res.get(count - 1)) {
                if (word.equals(currWord)) {
                    found = true;
                }
            }
            if (!found) {
                res.get(count - 1).add(currWord);
            }
        }

        for(int i = res.size() - 1; i >= 0; i--) {
            for(String word: res.get(i)) {
                System.out.println(word + ' ' + (i + 1));
            }
        }
    }

    public static void main(String[] args) {
//        checkPhoneNumbersFromFile(); // task 1
//        generateUserJsonFromFile(); // task 2
//        countWordsFromFile(); // task 3
    }
}