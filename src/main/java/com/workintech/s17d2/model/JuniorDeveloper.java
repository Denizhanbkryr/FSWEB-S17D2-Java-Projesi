package com.workintech.s17d2.model;

public class JuniorDeveloper extends Developer {
    public JuniorDeveloper(Integer id, String name, Double salary) {
        super(id, name, salary, Experience.JUNIOR);
    }
}
