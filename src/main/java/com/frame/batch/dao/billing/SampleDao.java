package com.frame.batch.dao.billing;

import com.frame.batch.dao.billing.entity.UserProfile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface SampleDao {

    UserProfile selectUser(@Param("userId") String userId) throws Exception;

}


