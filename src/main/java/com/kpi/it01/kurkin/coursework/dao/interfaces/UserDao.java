package com.kpi.it01.kurkin.coursework.dao.interfaces;

import com.kpi.it01.kurkin.coursework.exceptions.AlreadyExistsException;
import com.kpi.it01.kurkin.coursework.exceptions.DataBaseErrorException;
import com.kpi.it01.kurkin.coursework.exceptions.NoIdException;
import com.kpi.it01.kurkin.coursework.models.User;

public interface UserDao {
    User get(String login) throws DataBaseErrorException, NoIdException;
    void create(User user) throws DataBaseErrorException, AlreadyExistsException;
}
