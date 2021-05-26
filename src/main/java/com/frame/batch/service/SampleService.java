package com.frame.batch.service;

import com.frame.batch.dao.billing.entity.UserProfile;
import com.frame.batch.dao.dream.entity.NewOne;

import java.util.List;

public interface SampleService {


    /**
     * DB(billing) connection
     */
    UserProfile selectUser(String userId) throws Exception;

    /**
     * DB(dream) connection
     */
    List<NewOne> selectNews() throws Exception;


    /**
     * Redis Sample : Object
     */
    UserProfile registerUser(String userId) throws Exception;

    void deleteUser(String userId) throws Exception;

    UserProfile getUser(String userId) throws Exception;


    /**
     * Redis Sample : String
     */
    void blockUser(String userId);

    void unblockUser(String userId);

    boolean isUserBlocked(String userId);

    long getUserBlockedSecondsLeft(String userId);

    List<String> getUserIdList();

}
