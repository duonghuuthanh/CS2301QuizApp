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
public class ChoiceServices extends BaseServices<Choice>{
    private int questionId;

    public ChoiceServices(int questionId) {
        this.questionId = questionId;
    }
    

    @Override
    public PreparedStatement getStm(Connection conn) throws SQLException {
        PreparedStatement stm = conn.prepareCall("SELECT * FROM choice WHERE question_id=?");
        stm.setInt(1, questionId);
        
        return stm;
    }

    @Override
    public List<Choice> getResults(ResultSet rs) throws SQLException {
        List<Choice> choices = new ArrayList<>();
        while (rs.next()) {
            Choice c = new Choice(rs.getInt("id"), rs.getString("content"), rs.getBoolean("is_correct"));
            choices.add(c);
        }

        return choices;
    }
    
}
