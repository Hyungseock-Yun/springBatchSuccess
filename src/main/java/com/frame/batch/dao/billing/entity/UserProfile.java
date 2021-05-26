package com.frame.batch.dao.billing.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserProfile {

    private int seq;
    private String userId;
    private String userName;
    private String gender;
    private LocalDate birth;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedDate;
    private String createUser;
    private String updateUser;

}
