/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.utils;

import com.dht.services.BaseServices;
import com.dht.services.CategoryServices;
import com.dht.services.LevelServices;
import com.dht.services.questions.BaseQuestionServices;
import com.dht.services.questions.QuestionServices;
import com.dht.services.questions.UpdateQuestionServices;

/**
 *
 * @author admin
 */
public class MyConfigs {

    public static final UpdateQuestionServices uQService = new UpdateQuestionServices();
    public static final BaseServices lvlService = new LevelServices();
    public static final BaseQuestionServices quesService = new QuestionServices();
    public static final BaseServices cateService = new CategoryServices();
    public static final int NUM_QUES = 10;
    public static final double[] RATES = {0.4, 0.4, 0.2};
    
}
