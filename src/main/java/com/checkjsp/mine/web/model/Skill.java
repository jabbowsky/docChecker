package com.checkjsp.mine.web.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Skill {
    private int id;
    private String name;
    private String user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", name='" + name + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return name.equals(skill.getName()) && user.equals(skill.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id + ", " + name);
    }

    public Skill(int id, String user, String name) {
        this.id = id;
        this.user = user;
        this.name = name;
    }

}
