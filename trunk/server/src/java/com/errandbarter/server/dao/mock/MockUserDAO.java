/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.errandbarter.server.dao.mock;

import com.errandbarter.server.dao.UserDAO;
import com.errandbarter.server.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Zach
 */
public class MockUserDAO implements UserDAO {

    private Map<String, User> users;

    public MockUserDAO(Map<String, User> users) {
        this.users = users;
    }

    public User findById(String id) {
        return users.get(id);
    }

    public List<User> findAll() {
        return new ArrayList<User>(users.values());
    }

    public void makePersistent(User user) {
        users.put(user.getId(), user);
    }

}