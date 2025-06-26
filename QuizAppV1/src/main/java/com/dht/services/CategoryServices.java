/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.services;

import com.dht.pojo.Category;
import com.dht.utils.MyConnector;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class CategoryServices {

    public List<Category> getCates() throws SQLException {
        Connection conn = MyConnector.getInstance().connect();

        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM category");

        List<Category> cates = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");

            Category c = new Category(id, name);
            cates.add(c);
        }
        
        return cates;
    }
}
