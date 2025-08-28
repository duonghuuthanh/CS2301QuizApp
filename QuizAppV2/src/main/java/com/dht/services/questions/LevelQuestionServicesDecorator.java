/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.services.questions;

import java.util.List;

/**
 *
 * @author admin
 */
public class LevelQuestionServicesDecorator extends QuestionDecorator {
    private int levelId;

    public LevelQuestionServicesDecorator(BaseQuestionServices decorator, int lvlId) {
        super(decorator);
        this.levelId = lvlId;
    }

    @Override
    public String getSQL(List<Object> params) {
        String sql = this.decorator.getSQL(params) + " AND level_id=?";
        params.add(this.levelId);
        
        return sql;
    }
}
