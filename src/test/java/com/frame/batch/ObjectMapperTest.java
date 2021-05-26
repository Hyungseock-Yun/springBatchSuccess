package com.frame.batch;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

@RunWith(SpringRunner.class)
public class ObjectMapperTest {


    @Test
    public void objectToJson() throws JsonProcessingException {
        String expected = "{\"userId\":\"userId\",\"userPwd\":null,\"name\":\"tester\",\"authType\":\"web\",\"timestamp\":null,\"isUpdate\":false}";

        User user = new User("userId", "tester", "web");
        ObjectMapper objectMapper = new ObjectMapper();
        String userAsString = objectMapper.writeValueAsString(user);

        System.out.println("userAsString : " + userAsString);

        assert userAsString.equalsIgnoreCase(expected);
    }

    @Test
    public void jsonToObject() throws IOException {
        String expected = "{\"userId\":\"userId\",\"userPwd\":null,\"name\":\"tester\",\"authType\":\"web\",\"timestamp\":null,\"isUpdate\":false}";

        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(expected, User.class);
        assert user.getUserId().equalsIgnoreCase("userId");
    }




}

class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;
    private String userPwd;
    private String name;
    private String authType;
    private Date timestamp;
    private boolean isUpdate = false;

    public User() {
        super();
    }

    public User(String userId, String name, String authType) {
        super();
        this.userId = userId;
        this.name = name;
        this.authType = authType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", userPwd=" + userPwd + ", name=" + name + ", authType=" + authType
                + ", timestamp=" + timestamp + ", isUpdate=" + isUpdate + "]";
    }

}