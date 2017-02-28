package com.university.alumni.entity;

import javax.persistence.*;

/**
 * Created by wm on 2017/1/18.
 */
@Entity
@Table(name="User")
public class User {
    private Integer id;
    private String name;
    private String age;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",unique = true,nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name",nullable = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "age",nullable = true)
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
