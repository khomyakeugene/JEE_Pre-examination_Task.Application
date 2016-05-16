package com.company.calculator.model;

/**
 * Created by Yevhen on 16.05.2016.
 */
public interface UserDicDao {
    int getUserIdByName(String userName);

    int getCurrentUserId();

}
