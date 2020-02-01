package com.checkjsp.mine.web.service;

import java.sql.SQLException;
import java.util.*;

import com.checkjsp.mine.web.dao.DaoConnection;
import com.checkjsp.mine.web.model.SkillWord;
import org.springframework.stereotype.Service;

import com.checkjsp.mine.web.model.Skill;

@Service
public class SkillService {

    private static List<Skill> skills = new ArrayList<>();
    private static int skillCount = 0;
    private static Set<SkillWord> allSkills  = new HashSet<>();
    private static DaoConnection dao;

    static {
        try {
            dao = new DaoConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public SkillService() throws SQLException {
        allSkills = dao.getSkills();
    }

    public List<Skill> retrieveSkills(String user) {
        List<Skill> filteredSkills = new ArrayList<Skill>();
        for (Skill skill : skills) {
            if(skill.getUser().equalsIgnoreCase(user)){
                filteredSkills.add(skill);
            }
        }
        return filteredSkills;
    }

    public List<SkillWord> retrieveAllSkills() {
        List<SkillWord> listSkills = new ArrayList(allSkills);
        Collections.sort(listSkills, new SortAllWordById());
        return listSkills;
    }

    public Skill retrieveSkill(int id) {
        for (Skill skill : skills) {
            if (skill.getId()==id) {
                return skill;
            }
        }
        return null;
    }

    public void addSkill(String user, String name) {
        boolean skillFound = false;
        boolean numNotFound = true;
        int firstEmptyNum = 0;
        Collections.sort(skills, new SortById());
        for (Skill skill : skills) {
            if (skill.getUser().equalsIgnoreCase(user)){
                if(numNotFound) {
                    if (skill.getId() == firstEmptyNum ) { firstEmptyNum++; }
                    else { numNotFound = false; }
                }
                if(skill.getName().equals(name) ){
                    skillFound = true;
                }
            }
        }
        if(skillFound == false ){
            ++skillCount;
            skills.add(new Skill(firstEmptyNum,user, name));
            SkillWord word = new SkillWord(name);
            allSkills.add(word);
        }
    }

    public void deleteSkill(String user, int id){
        for (Iterator<Skill> itrSkill = skills.iterator(); itrSkill.hasNext(); ) {
            Skill curSkill = itrSkill.next();
            if (curSkill.getUser().equalsIgnoreCase(user) && curSkill.getId() == id ){
                itrSkill.remove();
                skillCount--;
            }
        }
    }

    public void clearSkills(String user){
        for (Iterator<Skill> itrSkill = skills.iterator(); itrSkill.hasNext(); ) {
            Skill curSkill = itrSkill.next();
            if (curSkill.getUser().equalsIgnoreCase(user) ){
                itrSkill.remove();
                skillCount--;
            }
        }
    }

    public void deleteSkillPerm(String user, int id) throws SQLException {
        for (Iterator<SkillWord> itrSkill = allSkills.iterator(); itrSkill.hasNext(); ) {
            SkillWord curSkill = itrSkill.next();
            if (curSkill.getId() == id ){
                curSkill.setDelete(true);
                curSkill.setSaved(false);
            }
        }
    }

    public void refreshAllSkilles() throws SQLException {
        for (Iterator<SkillWord> itrSkill = allSkills.iterator(); itrSkill.hasNext(); ) {
            SkillWord curSkill = itrSkill.next();
            if (curSkill.isSaved()){
                itrSkill.remove();
            }
        }
        Set<SkillWord> dbSkills = dao.getSkills();
        allSkills.addAll(dbSkills);
    }

    public void wirteToDatabase() throws SQLException {
        List<SkillWord> skillToSave = new ArrayList<>();
        List<SkillWord> skillToDelete = new ArrayList<>();
        for (Iterator<SkillWord> itrSkill = allSkills.iterator(); itrSkill.hasNext(); ) {
            SkillWord curSkill = itrSkill.next();
            if(!curSkill.isSaved()){
                if(curSkill.isDelete()){
                    skillToDelete.add(curSkill);
                } else {
                    skillToSave.add(curSkill);
                }
                curSkill.setSaved(true);
                itrSkill.remove();
            }
        }
        if( skillToSave.size() > 0 ){
            dao.insertSkill(skillToSave);
        }
        if( skillToDelete.size() > 0 ){
            dao.deleteSkill(skillToDelete);
        }
    }

    public int getSkillCount() {
        return skillCount;
    }

    private class SortById  implements Comparator<Skill> {
        public int compare(Skill a, Skill b) {
            return a.getId() - b.getId();
        }
    }

    private class SortAllWordById implements Comparator<SkillWord> {
        public int compare(SkillWord a, SkillWord b) {
            return a.getName().compareTo(b.getName());
        }
    }
}