package org.tbank;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        File data = new File("city.json");
        System.out.println("wow!");
        try {
            System.out.println(objectMapper.readValue(data, City.class));
        } catch (Exception e) {
            System.out.println("Что-то пошло не так:");
            e.printStackTrace();
        }
    }
}
