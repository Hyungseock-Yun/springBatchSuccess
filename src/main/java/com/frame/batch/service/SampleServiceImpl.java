package com.frame.batch.service;

import com.frame.batch.dao.billing.RedisDao;
import com.frame.batch.dao.billing.SampleDao;
import com.frame.batch.dao.billing.entity.UserProfile;
import com.frame.batch.dao.dream.NewOneDao;
import com.frame.batch.dao.dream.entity.NewOne;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class SampleServiceImpl implements SampleService {

    private final SampleDao sampleDao;
    private final NewOneDao newOneDao;
    private final RedisDao redisDao;

    public SampleServiceImpl(SampleDao sampleDao, NewOneDao newOneDao, RedisDao redisDao) {
        this.sampleDao = sampleDao;
        this.newOneDao = newOneDao;
        this.redisDao = redisDao;
    }

    @Override
    public UserProfile selectUser(String userId) throws Exception {
        System.out.println("[billingDataSource] selectUser start---------------------");
        return sampleDao.selectUser(userId);
    }

    @Override
    public List<NewOne> selectNews() throws Exception {
        System.out.println("[dreamDataSource] selectNews start-deleteUser--------------------");
        return newOneDao.selectNews();

    }

    @Override
    public UserProfile registerUser(String userId) throws Exception {
        UserProfile userProfile = sampleDao.selectUser(userId);
        if(userProfile != null){
            redisDao.setUser(userProfile);
        }
        return redisDao.getUser(userId);
    }

    @Override
    public void deleteUser(String userId) throws Exception {
        redisDao.deleteUser(userId);
    }

    @Override
    public UserProfile getUser(String userId) throws Exception {
        return redisDao.getUser(userId);
    }

    @Override
    public void blockUser(String userId) {
        redisDao.setUserBlocked(userId);
    }

    @Override
    public void unblockUser(String userId) {
        redisDao.deleteUserBlocked(userId);
    }

    @Override
    public boolean isUserBlocked(String userId) {
        return redisDao.isUserBlocked(userId);
    }

    @Override
    public long getUserBlockedSecondsLeft(String userId) {
        return redisDao.getUserBlockedSecondsLeft(userId);
    }

    @Override
    public List<String> getUserIdList() {
        return redisDao.getAllUsers();
    }

}
