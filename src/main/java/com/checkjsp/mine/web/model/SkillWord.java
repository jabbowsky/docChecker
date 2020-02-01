package com.checkjsp.mine.web.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SkillWord {

    private int id;
    private static int maxId = 0;
    private String name;
    private boolean saved = false;
    private boolean delete = false;
    private List<String> keyWords = new ArrayList<>();

    public SkillWord(String name ){
        this.id = maxId++;
        this.name = name;
        this.keyWords.add(name);
    }


    public SkillWord(int id, String name, boolean saved){
        this(name);
        this.id = id;
        this.saved = saved;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(List<String> keyWords) {
        this.keyWords = keyWords;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkillWord skillWord  = (SkillWord) o;
        return this.name.equals(skillWord.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
