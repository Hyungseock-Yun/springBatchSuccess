package com.frame.batch.controller;


import com.frame.batch.dao.billing.entity.UserProfile;
import com.frame.batch.service.SampleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/redis")
@Api(value = "레디스 샘플 Controller")
public class RedisController {


    private final SampleService service;

    @ApiOperation(value = "Redis UserProfile 입력")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "사용자아이디", required = true, dataType = "string", paramType = "path", defaultValue = "testUserId")
    })
    @PostMapping("/user/{userId}")
    public ResponseEntity<UserProfile> registerUser(@PathVariable String userId) throws Exception {
        service.registerUser(userId);
        return ResponseEntity.ok(service.registerUser(userId));
    }

    @ApiOperation(value = "Redis UserProfile 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "사용자아이디", required = true, dataType = "string", paramType = "path", defaultValue = "testUserId")
    })
    @DeleteMapping("/user/{userId}")
    public ResponseEntity deleteUser(@PathVariable String userId) throws Exception {
        service.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Redis UserProfile 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "사용자아이디", required = true, dataType = "string", paramType = "path", defaultValue = "testUserId")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<Object> getUser(@PathVariable String userId) throws Exception {
        UserProfile user = service.getUser(userId);
        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "Redis 사용자 블럭")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "사용자아이디", required = true, dataType = "string", paramType = "path", defaultValue = "testUserId")
    })
    @PostMapping("/user/block/{userId}")
    public ResponseEntity<String> blockUser(@PathVariable String userId) throws Exception {
        service.blockUser(userId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Redis 사용자 언블럭")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "사용자아이디", required = true, dataType = "string", paramType = "path", defaultValue = "testUserId")
    })
    @DeleteMapping("/user/block/{userId}")
    public ResponseEntity<String> unblockUser(@PathVariable String userId) throws Exception {
        service.unblockUser(userId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Redis 사용자 블럭 확인")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "사용자아이디", required = true, dataType = "string", paramType = "path", defaultValue = "testUserId")
    })
    @GetMapping("/user/block/{userId}")
    public ResponseEntity<Boolean> isUserBlocked(@PathVariable String userId) throws Exception {
        return ResponseEntity.ok(service.isUserBlocked(userId));
    }

    @ApiOperation(value = "Redis 사용자 블럭 시간 확인")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "사용자아이디", required = true, dataType = "string", paramType = "path", defaultValue = "testUserId")
    })
    @GetMapping("/user/block/left/{userId}")
    public ResponseEntity<Long> getUserBlockedSecondsLeft(@PathVariable String userId) throws Exception {
        return ResponseEntity.ok(service.getUserBlockedSecondsLeft(userId));
    }

//    @ApiOperation(value = "Redis 사용자 리스트 확인")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "userId", value = "사용자아이디", required = true, dataType = "string", paramType = "path", defaultValue = "testUserId")
//    })
//    @GetMapping("/users")
//    public ResponseEntity<Long> getUserIdList(@PathVariable String userId) throws Exception {
//        return ResponseEntity.ok(service.getUserBlockedSecondsLeft(userId));
//    }


}
