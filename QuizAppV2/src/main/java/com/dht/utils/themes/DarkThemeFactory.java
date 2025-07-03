/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.utils.themes;

import com.dht.quizappv2.App;

/**
 *
 * @author admin
 */
public class DarkThemeFactory implements ThemeFactory {

    @Override
    public String getStyleSheet() {
        return App.class.getResource("dark.css").toExternalForm();
    }
    
}
