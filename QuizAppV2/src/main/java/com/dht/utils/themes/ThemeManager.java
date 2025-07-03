/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.utils.themes;

import javafx.scene.Scene;

/**
 *
 * @author admin
 */
public class ThemeManager {
    private static ThemeFactory themeFactory = new DefaultThemeFactory();

    /**
     * @param aThemeFactory the themeFactory to set
     */
    public static void setThemeFactory(ThemeFactory aThemeFactory) {
        themeFactory = aThemeFactory;
    }
    
    public static void applyTheme(Scene scene) {
        scene.getRoot().getStylesheets().clear();
        scene.getRoot().getStylesheets().add(themeFactory.getStyleSheet());
    }
}
