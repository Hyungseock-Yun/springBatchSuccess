package com.frame.batch.dao.dream;

import com.frame.batch.dao.dream.entity.NewOne;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface NewOneDao {

    List<NewOne> selectNews() throws Exception;
}
