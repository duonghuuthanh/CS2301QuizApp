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
public class CategoryQuestionServicesDecorator extends QuestionDecorator {
    private int cateId;

    public CategoryQuestionServicesDecorator(BaseQuestionServices decorator, int cateId) {
        super(decorator);
        this.cateId = cateId;
    }

    @Override
    public String getSQL(List<Object> params) {
        String sql = this.decorator.getSQL(params) + " AND category_id=?";
        params.add(this.cateId);
        
        return sql;
    }
}
