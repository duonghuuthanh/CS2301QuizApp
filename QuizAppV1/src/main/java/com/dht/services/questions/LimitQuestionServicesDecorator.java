/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.services.questions;

import com.dht.pojo.Question;
import com.dht.services.BaseServices;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class LimitQuestionServicesDecorator extends QuestionServices {
    private int num;

    public LimitQuestionServicesDecorator(int num) {
        this.num = num;
    }

    @Override
    public PreparedStatement getStm(Connection conn) throws SQLException {
        PreparedStatement stm = conn.prepareCall("SELECT * FROM question ORDER BY rand() LIMIT ?");
        stm.setInt(1, num);
        
        return stm;
    }
 
    @Override
    public List<Question> getResults(ResultSet rs) throws SQLException {
        List<Question> questions = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            
            BaseServices s = new ChoiceServices(id);
             
            Question q = new Question.Builder(id, rs.getString("content")).addAllChoices(s.list()).build();

            questions.add(q);
        }

        return questions;
    }
}
