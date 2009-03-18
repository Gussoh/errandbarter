package com.errandbarter.server.dao;

import com.errandbarter.server.entity.Answer;
import java.util.List;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Zach
 */
public interface AnswerDAO extends GenericDAO<Answer, Integer> {

    public List<Answer> findByErrand(int errandId);
    public List<Answer> findByUser(String userId);
}