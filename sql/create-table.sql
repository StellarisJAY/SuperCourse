# 用户表
CREATE TABLE t_user(
	`id` VARCHAR(11) PRIMARY KEY COMMENT '11位手机号id',
	`nickname` VARCHAR(20) NOT NULL COMMENT '昵称',
	`password` VARCHAR(32) NOT NULL COMMENT '两次md5加密后的密码',
	`salt` VARCHAR(10) NOT NULL DEFAULT '1a2b3c4d5e' COMMENT 'md5 加密 salt',
	`head` VARCHAR(255) DEFAULT NULL COMMENT '头像链接',
	`description` VARCHAR(50) DEFAULT NULL COMMENT '个人简介', 
	`study_time` BIGINT DEFAULT 0 COMMENT '学习总时长，单位min',
	`register_time` DATETIME DEFAULT NULL COMMENT '注册时间',
	`last_login_time` DATETIME DEFAULT NULL COMMENT '上次登录时间',
	`user_type` SMALLINT DEFAULT 0 COMMENT '学生 0，老师 1',
	`course_count` INT DEFAULT 0 COMMENT '学生：关注课程数量，老师：发布课程数量'
	 # more 
);

# 课程 表
CREATE TABLE t_course(
	`id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '课程id',
	`name` VARCHAR(255) NOT NULL COMMENT '课程名',
	`image` VARCHAR(255) DEFAULT NULL COMMENT '课程图片',
	`teacher_id` VARCHAR(11) NOT NULL COMMENT '老师id',
	`teacher_name` VARCHAR(20) NOT NULL COMMENT '冗余字段：老师姓名',
	`status` SMALLINT DEFAULT 0 COMMENT '状态，0未开始、1进行中、2已结束',
	`price` DECIMAL(10, 2) DEFAULT '0.00' COMMENT '价格，0.00为免费课程',
	`start_time` DATETIME DEFAULT NULL COMMENT '开始时间',
	`end_time` DATETIME DEFAULT NULL COMMENT '结束时间，null表示永不结束'
	
	# more 
);

# 用户-课程关系表
CREATE TABLE t_user_course(
	`user_id` VARCHAR(11) NOT NULL COMMENT '学生id',
	`course_id` BIGINT NOT NULL COMMENT '课程id',
	`study_time` BIGINT DEFAULT '0' COMMENT '学习时长',
	`purchase_time` DATETIME DEFAULT NULL COMMENT '购买时间',
	`watched_chapter_count` INT DEFAULT '0' COMMENT '已学习章节数',
	`last_watched_chapter` BIGINT DEFAULT '1' COMMENT '上次学习章节id'
);

# 课程-章节表
CREATE TABLE t_course_chapter(
	`id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '章节id',
	`course_id` BIGINT NOT NULL COMMENT '课程id',
	`name` VARCHAR(255) NOT NULL COMMENT '章节名称'
);

DROP TABLE t_video;
# 视频表
CREATE TABLE t_video(
	`id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '视频id',
	`vid` VARCHAR(255) NOT NULL COMMENT '视频点播vid',
	`cover_url` VARCHAR(255) DEFAULT NULL COMMENT '封面url',
	`title` VARCHAR(255) DEFAULT 'VIDEO' COMMENT '',
	`chapter_id` BIGINT NOT NULL COMMENT '视频所属章节id',
	`course_id` BIGINT NOT NULL COMMENT '课程id',
	`duration` INT NOT NULL COMMENT '时长，单位s'
);

DROP TABLE t_watch_record;
# 观看记录表
CREATE TABLE t_watch_record(
	`id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '观看记录id',
	`user_id` VARCHAR(11) NOT NULL COMMENT '用户id',
	`video_id` BIGINT NOT NULL COMMENT '视频id',
	`course_id` BIGINT NOT NULL COMMENT '课程id',
	`time` INT NOT NULL COMMENT '观看到时间',
	`finished` SMALLINT DEFAULT 0 COMMENT '是否已完成, 0未完成，1已完成'
);

# 练习表
CREATE TABLE t_practice(
	`id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
	`title` VARCHAR(255) NOT NULL COMMENT '标题',
	`course_id` BIGINT NOT NULL COMMENT '所属课程id',
	`chapter_id` BIGINT NOT NULL COMMENT '所属章节id',
	`time_limit` INT DEFAULT 0 COMMENT '时间限制，单位s，0为不限时'
);

# 选择题表
CREATE TABLE t_choice_question(
	`id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
	`content` VARCHAR(255) NOT NULL COMMENT '题干',
	`multi` INT DEFAULT 0 COMMENT '是否是多选，0 false  1 true',
	`selection_a` VARCHAR(255) NOT NULL COMMENT 'A',
	`selection_b` VARCHAR(255) NOT NULL COMMENT 'B',
	`selection_c` VARCHAR(255) NOT NULL COMMENT 'C',
	`selection_d` VARCHAR(255) NOT NULL COMMENT 'D'
);
# 选择题答案表
CREATE TABLE t_choice_answer(
	`qid` BIGINT NOT NULL COMMENT '题目id',
	`answer` INT NOT NULL COMMENT '正确选项，A 1 B 2 C 3 D 4'
);


# 题目集
DROP TABLE t_collection;
CREATE TABLE t_collection(
	`id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
	`name` VARCHAR(255) NOT NULL COMMENT '名称',
	`teacher_id` VARCHAR(11) NOT NULL COMMENT '教师id',
	`private_collection` INT DEFAULT 0 COMMENT '私人题集 1 true  0 false'
);

# 题集-题目关系表
CREATE TABLE t_collection_question(
	`qid` BIGINT NOT NULL COMMENT '题目id',
	`cid` BIGINT NOT NULL COMMENT '题目集id'
);


DROP TABLE t_question;
CREATE TABLE t_question(
	`id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
	`content` VARCHAR(255) NOT NULL COMMENT '题干',
	`type` INT DEFAULT 1 COMMENT '题目类型： 1单选 2多选 3填空',
	`selection_a` VARCHAR(255) COMMENT 'A',
	`selection_b` VARCHAR(255) COMMENT 'B',
	`selection_c` VARCHAR(255) COMMENT 'C',
	`selection_d` VARCHAR(255) COMMENT 'D'
);

CREATE TABLE t_answer(
	`qid` BIGINT COMMENT 'qid',
	`answer` VARCHAR(255) COMMENT '答案'
);

CREATE TABLE t_practice_question(
	`pid` BIGINT NOT NULL COMMENT '练习id',
	`qid` BIGINT NOT NULL COMMENT '题目id',
	`score` FLOAT NOT NULL COMMENT '该题目分数'
);

CREATE TABLE t_practice_record(
	`uid` BIGINT NOT NULL COMMENT '用户id',
	`pid` BIGINT NOT NULL COMMENT '练习id',
	`time_used` INT NOT NULL COMMENT '用时，单位s',
	`finish_time` DATETIME NOT NULL COMMENT '提交时间',
	`score` FLOAT NOT NULL COMMENT '得分'
);

