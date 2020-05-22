package io.niufen.springboot.thymeleaf.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserBO implements Serializable {
    private static final long serialVersionUID = -2420208110744403377L;
    private String name;
    private Integer age;
    private String pass;

    public UserBO(String name, Integer age, String pass) {
        this.name = name;
        this.age = age;
        this.pass = pass;
    }

}
