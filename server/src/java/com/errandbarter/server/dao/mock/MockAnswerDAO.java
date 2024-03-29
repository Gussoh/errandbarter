/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.errandbarter.server.dao.mock;

import com.errandbarter.server.dao.AnswerDAO;
import com.errandbarter.server.entity.Answer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Zach
 */
public class MockAnswerDAO implements AnswerDAO {
    
    private Map<Integer, Answer> answers;
    
    public MockAnswerDAO(Map<Integer, Answer> answers) {
        this.answers = answers;
    }
    
    public Answer findById(Integer id) {
        return answers.get(id);
    }
    
    public List<Answer> findAll() {
        return new ArrayList<Answer>(answers.values());
    }
    
    public void makePersistent(Answer answer) {
        synchronized(this) {
            if (answer.getId() == null) {
                //we assume max (answer.id) = answers.length;
                answer.setId(answers.size() + 1);
            }
            answers.put(answer.getId(), answer);
        }
    }
    
    public List<Answer> findByUser(String user) {
        List<Answer> results = new ArrayList<Answer>();
        
        for (Answer answer : answers.values()) {
            String _user = answer.getUserID();
            if (_user != null && _user.equals(user)) {
                results.add(answer);
            }
        }
        
        return results;
    }
    
    public List<Answer> findByErrand(int errandID) {
        List<Answer> results = new ArrayList<Answer>();
        
        for (Answer answer : answers.values()) {
            if (answer.getErrandID() == errandID) {
                results.add(answer);
            }
        }
        return results;
    }
    
}
