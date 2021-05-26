# 스프링 배치 프로젝트 샘플

# 1.환경구성
 - SpringBoot 2.4.5
 - Java 11
 - Gradle 7.0
 - Embed Tomcat 9.0.45

# 2.기능구성
 - Active Profile (-Dspring.profiles.active=[ local | dev ]
 - Quartz Scheduler v.2.3.2
 - Spring Batch
 - Swagger 2.9.2
 - Mybatis

# 3.빌드 & 배포
 - gradlew clean build(or assemble)
 - jar location : ./bulid/libs/batch-0.0.1-SNAPSHOT.jar

# 4. 실행
 > java -jar build/libs/batch-0.0.1-SNAPSHOT.jar --spring.profiles.active=local

# 5. 테스트 & 호출
 > http://localhost:8080/swagger-ui.html
 

# 6. 소스 작성방법
 
 1. Job 생성
 2. Job > Step 생성
 3. Job > Step > tasklet 생성



 4. Scheduler 등록(생성한 Job 호출) - Quartz
 5. Controller 호출 API 등록(생성한 Job 호출) - OneTime 테스트 용

# 7. 로컬 환경구성
# 7-1.Docker 설치
## 7-1-1. Docker 설치
## 7-2-2. Kitematic 설치 -> Docker Cli 실행

# 7-2. Mysql 설치    
## 7-2-1. Mysql Docker Image 조회
> docker search mysql
 
## 7-2-2. Mysql Docker Image Download
> docker pull mysql
 
## 7-2-3. Mysql Docker Image 확인
> docker images
 
## 7-2-4. Mysql Docker container 생성
> docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=password --name mysql_sample mysql
 
## 7-2-5. Mysql Docker container 동작 확인
> docker ps -a
 
# 7-3. Redis 설치
## 7-3-1 레디스 이미지 가져오기
> docker pull redis

## 7-3-2 레디스 config 파일 생성 - redis.conf


## 7-3-3 레디스 컨테이서 실행
> docker run --name sample-redis -d -p 6001:6000 -v C:\redis\redis.conf:/usr/local/etc/redis/redis.conf redis redis-server /usr/local/etc/redis/redis.conf

## 7-3-4 레디스 실행 확인
> docker ps -a



# 7-4. 스키마 생성 & 테이블 생성
## 7-4-1. 스키마 생성: billing

## 7-4-2. 배치 테이블 생성 : schema-mysql.sql 파일 검색하여 테이블 생성

     - BATCH_JOB_INSTANCE
     - BATCH_JOB_EXECUTION
     - BATCH_JOB_EXECUTION_PARAMS
     - BATCH_STEP_EXECUTION
     - BATCH_STEP_EXECUTION_CONTEXT
     - BATCH_JOB_EXECUTION_CONTEXT
     - BATCH_STEP_EXECUTION_SEQ
     - BATCH_JOB_EXECUTION_SEQ
     - BATCH_JOB_SEQ

## 7-4-3 테스트 테이블 생성 - userProfile
      
        CREATE TABLE userProfile (
          seq int NOT NULL AUTO_INCREMENT,
          userId varchar(45) DEFAULT NULL,
          userName varchar(45) DEFAULT NULL,
          gender char(1) DEFAULT NULL,
          birth date DEFAULT NULL,
          createdDate datetime DEFAULT NULL,
          updatedDate datetime DEFAULT NULL,
          createUser varchar(45) DEFAULT NULL,
          updateUser varchar(45) DEFAULT NULL,
          PRIMARY KEY (seq)
        ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='사용자정보';



## 7-4-4. 스키마 : dream (MultiDataBase 테스트를 위한 샘플 스키마)

## 7-4-5. 테스트 테이블 생성 - newOne

       CREATE TABLE newOne (
          seq int NOT NULL AUTO_INCREMENT,
          name varchar(45) DEFAULT NULL,
          PRIMARY KEY (seq)
       ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


#8 로그(LOGBACK) 설정
## 8-1 로그별 폴더, 로그파일 설정
 - default : ./logs/log/
 - batch : ./logs/batch/
 - api : ./logs/api/
 - db : ./logs/db/ 

## 8-2 로그파일크기, 로그보존기간 설정

## 8-3 프로파일 로그 config 설정
 - logback-local.properties
 - logback-dev.properties

## 8-4 로그설정 파일
 - logback-spring.xml