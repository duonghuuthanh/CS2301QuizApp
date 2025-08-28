/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.utils;

import com.dht.services.CategoryServices;
import com.dht.services.LevelServices;
import com.dht.services.questions.BaseQuestionServices;
import com.dht.services.questions.QuestionServices;
import com.dht.services.questions.UpdateQuestionServices;

/**
 *
 * @author admin
 */
public class Configs {

    public static final LevelServices levelServices = new LevelServices();
    public static final BaseQuestionServices questionServices = new QuestionServices();
    public static final UpdateQuestionServices uQuestionServices = new UpdateQuestionServices();
    public static final CategoryServices cateServices = new CategoryServices();
    
}
