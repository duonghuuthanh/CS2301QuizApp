/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.services.exam;

import com.dht.pojo.Question;
import com.dht.services.questions.BaseQuestionServices;
import com.dht.services.questions.LevelQuestionServicesDecorator;
import com.dht.services.questions.LimitedQuestionServicesDecorator;
import com.dht.utils.Configs;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class FixedStrategy extends ExamStrategy {

    @Override
    public List<Question> getQuestions() throws SQLException {
        List<Question> questions = new ArrayList<>();
        
        for (int i = 0; i < Configs.RATES.length; i++) {
            BaseQuestionServices s = new LimitedQuestionServicesDecorator(new LevelQuestionServicesDecorator(Configs.questionServices, i + 1), (int)(Configs.RATES[i]*Configs.NUM_QUES));
            questions.addAll(s.list());
        }
        
        return questions;
    }
    
}
