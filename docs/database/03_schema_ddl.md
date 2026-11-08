# 스키마 DDL

## 작성 정보

- 작성자: 이승건
- 작성일: 2026.11.07
- version: 1.0

## DCL
```
-- 1. DB 껍데기 생성
CREATE DATABASE IF NOT EXISTS plan_ping
    CHARACTER SET = utf8mb4
    COLLATE = UTF8MB4_UNICODE_CI;

-- 2. 서비스 전용 사용자 계정 생성 ('사용자', '비밀번호' 부분은 원하시는 것으로 변경)
CREATE USER '사용자'@'%' IDENTIFIED BY '비밀번호';
CREATE USER '사용자'@'localhost' IDENTIFIED BY '비밀번호';

-- 3. 생성한 DB에 대해서만 모든 권한 부여
GRANT ALL PRIVILEGES ON plan_ping.* TO '사용자'@'%';
GRANT ALL PRIVILEGES ON plan_ping.* TO '사용자'@'localhost';

-- 4. 권한 적용 (새로고침)
FLUSH PRIVILEGES;

-- 5. 계정 권한 확인
SHOW GRANTS FOR '사용자'@'%';

-- 현재 살아있는 계정 확인
USE mysql;
SELECT host, user FROM USER;


```

## DDL

```
-- -----------------------------------------------------------------
-- 테이블 삭제
SET FOREIGN_KEY_CHECKS = 0; -- 1. 외래 키 검사 기능 끄기

DROP TABLE IF EXISTS `role`;

DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `login_history`;

DROP TABLE IF EXISTS `routine_plan`;
DROP TABLE IF EXISTS `routine_plan_item`;

DROP TABLE IF EXISTS `daily_plan`;
DROP TABLE IF EXISTS `daily_plan_item`;
DROP TABLE IF EXISTS `daily_plan_item_detail`;

DROP TABLE IF EXISTS `alarm`;


SET FOREIGN_KEY_CHECKS = 1; -- 2. 다 지운 후 반드시 다시 켜기!
-- -----------------------------------------------------------------

CREATE TABLE `role` (
	`id`	INT	NOT NULL AUTO_INCREMENT,
	`name`	VARCHAR(20)	NOT NULL,
	`description`	VARCHAR(255)	NOT NULL,

	PRIMARY KEY(`id`)
);

-- -----------------------------------------------------------------

CREATE TABLE `users` (
	`id`	INT	NOT NULL	AUTO_INCREMENT,
	`role_id`	INT	NOT NULL,
	`account`	VARCHAR(20)	NOT NULL,
	`password`	VARCHAR(255)	NOT NULL,
	`email`	VARCHAR(255)	NULL,
	`phone`	VARCHAR(255)	NULL,
	`created_at`	DATETIME	NOT NULL	DEFAULT CURRENT_TIMESTAMP,
	`updated_at`	DATETIME	NULL,
	`is_deleted`	TINYINT(1)	NOT NULL	DEFAULT 0,
	`is_locked`	TINYINT(1)	NOT NULL	DEFAULT 0,

	PRIMARY KEY(`id`),
	CONSTRAINT `FK_role_TO_users_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
);

CREATE TABLE `login_history` (
	`id`	INT	NOT NULL AUTO_INCREMENT,
	`user_id`	INT	NOT NULL,
	`login_at`	DATETIME	NOT NULL	DEFAULT CURRENT_TIMESTAMP,
	`is_success`	TINYINT(1)	NOT NULL	DEFAULT 0,
	`ip_address`	VARCHAR(255)	NULL,

	PRIMARY KEY(`id`),
	CONSTRAINT `FK_users_TO_login_history_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

-- -----------------------------------------------------------------

CREATE TABLE `routine_plan` (
	`id`	INT	NOT NULL AUTO_INCREMENT,
	`user_id`	INT	NOT NULL,
	`day_of_week`	VARCHAR(10)	NOT NULL,
	`is_deleted`	TINYINT(1)	NOT NULL,

	PRIMARY KEY(`id`),
	CONSTRAINT `FK_users_TO_routine_plan_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

CREATE TABLE `routine_plan_item` (
	`id`	INT	NOT NULL AUTO_INCREMENT,
	`routine_plan_id`	INT	NOT NULL,
	`start_time`	TIME	NOT NULL,
	`end_time`	TIME	NOT NULL,
	`content`	VARCHAR(255)	NOT NULL,
	`is_alarm`	TINYINT(1)	NOT NULL	DEFAULT 0,

	PRIMARY KEY(`id`),
	CONSTRAINT `FK_routine_plan_TO_routine_plan_item_1` FOREIGN KEY (`routine_plan_id`) REFERENCES `routine_plan` (`id`)
);

-- -----------------------------------------------------------------

CREATE TABLE `daily_plan` (
	`id`	INT	NOT NULL AUTO_INCREMENT,
	`user_id`	INT	NOT NULL,
	`target_date`	DATE	NOT NULL	DEFAULT CURRENT_DATE,
	`is_delet`	TINYINT(1)	NOT NULL,
	
	PRIMARY KEY(`id`),
	CONSTRAINT `FK_users_TO_daily_plan_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
);

CREATE TABLE `daily_plan_item` (
	`id`	INT	NOT NULL AUTO_INCREMENT,
	`daily_plan_id`	INT	NOT NULL,
	`start_time`	TIME	NOT NULL,
	`end_time`	TIME	NOT NULL,
	`content`	VARCHAR(10)	NOT NULL,
	`is_alarm`	TINYINT(1)	NOT NULL	DEFAULT 0,
	`is_done`	TINYINT(1)	NOT NULL	DEFAULT 0,
	`review`	TEXT	NULL,

	PRIMARY KEY(`id`),
	CONSTRAINT `FK_daily_plan_TO_daily_plan_item_1` FOREIGN KEY (`daily_plan_id`) REFERENCES `daily_plan` (`id`)
);

CREATE TABLE `daily_plan_item_detail` (
	`id`	INT	NOT NULL	AUTO_INCREMENT,
	`daily_plan_item_id`	INT	NOT NULL,
	`detail`	TEXT	NOT NULL,
	`target_count`	INT	NOT NULL	DEFAULT 1,

	PRIMARY KEY(`id`)
	CONSTRAINT `FK_daily_plan_item_TO_daily_plan_item_detail_1` FOREIGN KEY (`daily_plan_item_id`)REFERENCES `daily_plan_item` (`id`)
);

-- -----------------------------------------------------------------

CREATE TABLE `alarm` (
	`id`	INT	NOT NULL AUTO_INCREMENT,
	`user_id`	INT	NOT NULL,
	`content`	VARCHAR(255)	NOT NULL,
	`created_at`	DATETIME	NOT NULL	DEFAULT CURRENT_TIMESTAMP,
	`is_read`	TINYINT(1)	NOT NULL	DEFAULT 0,

	PRIMARY KEY(`id`),
	CONSTRAINT `FK_users_TO_alarm_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);
```

