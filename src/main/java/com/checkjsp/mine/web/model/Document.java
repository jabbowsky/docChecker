package com.checkjsp.mine.web.model;

import java.util.ArrayList;
import java.util.List;

public class Document {
    private int id;
    private String header;
    private String text;
    private String user;
    private List<SkillWord> skills = new ArrayList<>();

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Document(int id, String user, String header, String text, List<SkillWord> skills) {
        this.id = id;
        this.user = user;
        this.header = header;
        this.text = text;
        this.skills = skills;
    }

    public String getHeader() {
        return header;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<SkillWord> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillWord> skills) {
        this.skills = skills;
    }

}
