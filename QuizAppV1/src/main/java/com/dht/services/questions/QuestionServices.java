/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.services.questions;

import com.dht.pojo.Choice;
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
public class QuestionServices extends BaseServices<Question> {

    @Override
    public PreparedStatement getStm(Connection conn) throws SQLException {
        return conn.prepareCall("SELECT * FROM question");
    }

    @Override
    public List<Question> getResults(ResultSet rs) throws SQLException {
        List<Question> questions = new ArrayList<>();
        while (rs.next()) {
            Question q = new Question.Builder(rs.getInt("id"), rs.getString("content")).build();

            questions.add(q);
        }

        return questions;
    }
    
}
