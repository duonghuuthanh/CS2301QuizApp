/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.services.questions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class KeywordQuestionServicesDecorator extends QuestionServices {
    private String kw;

    public KeywordQuestionServicesDecorator(String kw) {
        this.kw = kw;
    }

    @Override
    public PreparedStatement getStm(Connection conn) throws SQLException {
        PreparedStatement stm = conn.prepareCall("SELECT * FROM question WHERE content like concat('%', ?, '%')");
        stm.setString(1, kw);
        
        return stm;
    }
}
