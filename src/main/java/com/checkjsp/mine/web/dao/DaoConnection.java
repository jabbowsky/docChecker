package com.checkjsp.mine.web.dao;

import com.checkjsp.mine.web.model.SkillWord;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DaoConnection {

    private static Connection connection;
    private PreparedStatement insertSkillSql;
    private PreparedStatement deleteSkillSql;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hr?useSSL=false&allowPublicKeyRetrieval=true","hr","hr");
            connection.setAutoCommit(false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public DaoConnection() throws SQLException {
        insertSkillSql = connection.prepareStatement("insert into skills(skill_name, skill_id) values (?,?)");
        deleteSkillSql = connection.prepareStatement("delete from skills where skill_id = ?");
    }

    public Set<SkillWord> getSkills() throws SQLException {
        Statement stmt= connection.createStatement();
        ResultSet rs=stmt.executeQuery("select skill_id, skill_name from skills");
        Set<SkillWord> skills = new HashSet<SkillWord>();
        while(rs.next()){
            skills.add(new SkillWord(rs.getInt(1),rs.getString(2),true));
        }
        return skills;
    }

    public void deleteSkill(List<SkillWord> skills) throws SQLException {
        for(SkillWord skillWord : skills){
            deleteSkillSql.setInt(1,skillWord.getId());
            deleteSkillSql.addBatch();
        }
        deleteSkillSql.executeBatch();
        connection.commit();
    }

    public void insertSkill(List<SkillWord> skills) throws SQLException {
        for(SkillWord skillWord : skills){
            insertSkillSql.setString(1,skillWord.getName());
            insertSkillSql.setInt(2,skillWord.getId());
            insertSkillSql.addBatch();
        }
        insertSkillSql.executeBatch();
        connection.commit();
    }

}
