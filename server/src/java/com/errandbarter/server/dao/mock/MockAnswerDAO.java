/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.errandbarter.server.dao.mock;

import com.errandbarter.server.dao.AnswerDAO;
import com.errandbarter.server.entity.Answer;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 *
 * @author Zach
 */
public class MockAnswerDAO implements AnswerDAO {

    private Hashtable<Integer, Answer> answers;

    public MockAnswerDAO(Hashtable<Integer, Answer> answers) {
        this.answers = answers;
    }

    public Answer findById(Integer id) {
        return answers.get(id);
    }

    public List<Answer> findAll() {
        return new ArrayList<Answer>(answers.values());
    }

    public void makePersistent(Answer answer) {
        if (answer.getId() == null) {
            answer.setId(answers.size() + 1);
        }
        answers.put(answer.getId(), answer);
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
