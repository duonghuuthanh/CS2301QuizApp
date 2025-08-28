/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.services;

import com.dht.utils.JdbcConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author admin
 */
public abstract class BaseServices<T> {
    public abstract PreparedStatement getStm(Connection conn) throws SQLException;
    public abstract List<T> getResults(ResultSet rs) throws SQLException;
    
    public List<T> list() throws SQLException {// template method
        // Mở kết nối
        Connection conn = JdbcConnector.getInstance().connect();

        // Truy vấn
        PreparedStatement stm = this.getStm(conn);

        return this.getResults(stm.executeQuery());
    }
}
