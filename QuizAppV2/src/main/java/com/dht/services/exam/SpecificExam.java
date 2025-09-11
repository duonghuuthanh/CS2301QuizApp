/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.services.exam;

import com.dht.pojo.Question;
import com.dht.services.questions.BaseQuestionServices;
import com.dht.services.questions.LimitedQuestionServicesDecorator;
import com.dht.utils.Configs;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author admin
 */
public class SpecificExam extends ExamStrategy {
    private int num;

    public SpecificExam(int num) {
        this.num = num;
    }

    @Override
    public List<Question> getQuestions() throws SQLException {
        BaseQuestionServices s = new LimitedQuestionServicesDecorator(Configs.questionServices, this.num);
        return s.list();
    }
    
}
