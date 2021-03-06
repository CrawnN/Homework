
# 数据库相关操作总结报告
## 命令登录
```sql
C:\Users\wangf-t>mysql -uroot -pcrawn@420
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 130
Server version: 5.5.55 MySQL Community Server (GPL)

Copyright (c) 2000, 2017, Oracle and/or its affiliates. All rights reserved.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql>
```
## 权限管理
### 添加用户并附权限
- grant 权限 on 数据库或表 to 用户名@’登陆IP地址’
    - 权限：查询、插入、更新、删除 或 全部权限（all privileges ）
    - 数据库或表：数据库名 或 数据库.表名
- 或 跟数据库下，超级用户执行：insert into user ...
### 更新用户密码
- update user set password=password('新密码') where 条件（锁定用户）;
### 更改用户权限或删除用户
- rovoke 权限 on 数据库或表 to 用户名@’登陆IP地址’
    - 权限：查询、插入、更新、删除 或 全部权限（all privileges ）
    - 数据库或表：数据库名 或 数据库.表名
- 或 跟数据库下，超级用户执行：delete from user where user='用户名' and host='IP地址';//删除用户所有权限
### 注意
- 执行完权限操作后最好执行：flush privileges; 使权限加载
- show grants; //查看当前用户权限
- show grants for 用户名;//查看特定用户权限
## 数据库的创建、删除
### 创建数据库
- show databases;//显示当前拥有的所有的数据库
- create database databasename
```sql
mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| db                 |
| mysql              |
| nan                |
| performance_schema |
| spring             |
| test               |
+--------------------+
7 rows in set (0.00 sec)

mysql> create database workflow
    -> ;
Query OK, 1 row affected (0.01 sec)

mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| db                 |
| mysql              |
| nan                |
| performance_schema |
| spring             |
| test               |
| workflow           |
+--------------------+
8 rows in set (0.00 sec)

mysql>
```
### 删除数据库
- drop database databasename
```sql
mysql> drop database test;
Query OK, 0 rows affected

mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| db                 |
| mysql              |
| nan                |
| performance_schema |
| spring             |
| workflow           |
+--------------------+
7 rows in set (0.00 sec)

mysql>
```
## 表操作
### 进入指定数据库
- use databasename;
```sql
mysql> use workflow
Database changed
mysql>
```
### 创建表格并指定相应属性
- create table 表名(字段名   字段属性  为空？    自增？  唯一？ 默认值？ COMMENT '声明'  主键？,<br>...);
    - <b>注意：</b>
        - 时间相关属性中只有timestamp可以设定default，其他都不可以，会报错
        - 同时只能设定唯一一个默认值为current_timestamp的字段
        - 每个字段用“,”隔开
        - sex类似属性可以设定成int（1）并做出声明——eg）：COMMENT '0:Man 1:Woman'
        - mysql语句最好不要出现汉字——会乱码（默认latin1编码，建议在图形界面中更改）；
```sql
<!--创建员工信息表-->
mysql> create table employee (
    -> id int(11) not null primary key,
    -> name varchar(20) not null,
    -> sex int(1) comment '0:男 1:女',
    -> title int(5) not null,
    -> department int(5) not null,
    -> tell varchar(20));
Query OK, 0 rows affected (0.02 sec)
<!--创建职称表，分离职称信息并依据职称id、部门id记录层级直属关系-->
mysql> create table title(
    -> id int(5) not null primary key,
    -> title varchar(20) not null,
    -> description varchar(255));
Query OK, 0 rows affected (0.02 sec)
<!--创建部门表，分离部门信息并依据职称id、部门id记录层级直属关系-->
mysql> create table department(
    -> id int(5) not null primary key,
    -> title varchar(20) not null,
    -> description varchar(255));
Query OK, 0 rows affected (0.02 sec)
<!--创建流程类型表，分离流程类型信息-->
mysql> create table workflowtype (
    -> id int(5) not null primary key,
    -> type varchar(40) not null);
Query OK, 0 rows affected (0.02 sec)
<！--创建流程表，记录流程相关信息同时设置主键(注意posttime和updatetime的设置（不要用中文T_T!）)-->
mysql> create table check_flow(
    -> id int(20) not null auto_increment primary key,
    -> sponsor int(11) not null,
    -> checkman int(11) not null,
    -> directleader int(11) not null,
    -> title varchar(30) not null,
    -> type int(5) not null,
    -> description varchar(255),
    -> postdate datetime null comment '创建时间',
    -> updatetime timestamp null default CURRENT_TIMESTAMP on update CURRENT_TIM
ESTAMP comment '更新时间',
    -> foreign key(sponsor) references employee(id),
    -> foreign key(checkman) references employee(id),
    -> foreign key(directleader) references employee(id),
    -> foreign key(type) references workflowtype(id));
Query OK, 0 rows affected (0.03 sec)

mysql> show tables
    -> ;
+--------------------+
| Tables_in_workflow |
+--------------------+
| check_flow         |
| department         |
| employee           |
| title              |
| workflowtype       |
+--------------------+
5 rows in set (0.00 sec)

mysql>
```
### 更新表字段属性
- alter table tablename add (COLUMN) 字段名 字段类型定义,//添加字段，COLUNMN关键字可加可不加
- alter table tablename drop (COLUMN) 字段名,//删除字段
- alter table tablename change (COLUMN) 字段名 新字段名 字段类型,//更改字段名（<b>注意：必须重新定义字段类型</b>）
- alter table tablename MODIFY (COLUMN) 字段名 字段类型定义,//更改字段类型
```sql
<!--将employee表的字段更新成外接主键-->
mysql> alter table employee add foreign key(title) references title(id);
Query OK, 0 rows affected (0.04 sec)
Records: 0  Duplicates: 0  Warnings: 0
mysql> alter table employee add foreign key(department) references department(id
);
Query OK, 0 rows affected (0.04 sec)
Records: 0  Duplicates: 0  Warnings: 0
<!--字段名创建错误，添加失败，修改department表的字段名后重新插入数据-->
mysql> insert into department(id,department) values(1,'Department of Manager'),
    -> (wd2,'Deparment of Auditingdaw Department');
ERROR 1054 (42S22): Unknown column 'department' in 'field list'

mysql> alter table department change title department varchar(40) not null;
Query OK, 0 rows affected (0.04 sec)

Records: 0  Duplicates: 0  Warnings: 0
mysql> insert into department(id,department) values(1,'Manager Department'),
    -> (2,'Auditing Department'),
    -> (3,'Product Department');
Query OK, 3 rows affected (0.01 sec)
Records: 3  Duplicates: 0  Warnings: 0
mysql> select * from department;
+----+---------------------+-------------+
| id | department          | description |
+----+---------------------+-------------+
|  1 | Manager Department  | NULL        |
|  2 | Auditing Department | NULL        |
|  3 | Product Department  | NULL        |
+----+---------------------+-------------+
3 rows in set (0.00 sec)

mysql>

```
### 向表中插入记录
- insert into tablename (属性列表) values（属性对应的值）, ... ,(属性对应的值);
```sql
mysql> select * from title;
Empty set (0.00 sec)
mysql> insert into title (id,title) values(1,'President');
Query OK, 1 row affected (0.01 sec)
mysql> insert into title (id,title) values(2,'Vice President');
Query OK, 1 row affected (0.01 sec)
mysql> insert into title (id,title) values(3,'General Manager');
Query OK, 1 row affected (0.01 sec)
<--插入多条记录-->
mysql> insert into title (id,title) values(5,'Learder'),
    -> (6,'Staff');
Query OK, 2 rows affected (0.01 sec)
Records: 2  Duplicates: 0  Warnings: 0
mysql> select * from title
    -> ;
+----+-----------------+-------------+
| id | title           | description |
+----+-----------------+-------------+
|  1 | President       | NULL        |
|  2 | Vice President  | NULL        |
|  3 | General Manager | NULL        |
|  4 | Manager         | NULL        |
|  5 | Learder         | NULL        |
|  6 | Staff           | NULL        |
+----+-----------------+-------------+
6 rows in set (0.00 sec)
mysql> insert into employee (id,name,title,department,sex) values
    -> (1,'Crawn',1,1,0),
    -> (2,'Jack',2,2,0),
    -> (3,'Rose',2,3,1),
    -> (4,'Bob',3,2,0),
    -> (5,'Lee',4,2,1),
    -> (6,'Liu',5,2,1),
    -> (7,'Liang',6,2,1),
    -> (8,'Kun',4,3,0),
    -> (9,'Nan',5,3,1),
    -> (10,'Wang',6,3,0);
Query OK, 10 rows affected (0.01 sec)
Records: 10  Duplicates: 0  Warnings: 0

mysql> select * from employee
    -> ;
+----+-------+------+-------+------------+------+
| id | name  | sex  | title | department | tell |
+----+-------+------+-------+------------+------+
|  1 | Crawn |    0 |     1 |          1 | NULL |
|  2 | Jack  |    0 |     2 |          2 | NULL |
|  3 | Rose  |    1 |     2 |          3 | NULL |
|  4 | Bob   |    0 |     3 |          2 | NULL |
|  5 | Lee   |    1 |     4 |          2 | NULL |
|  6 | Liu   |    1 |     5 |          2 | NULL |
|  7 | Liang |    1 |     6 |          2 | NULL |
|  8 | Kun   |    0 |     4 |          3 | NULL |
|  9 | Nan   |    1 |     5 |          3 | NULL |
| 10 | Wang  |    0 |     6 |          3 | NULL |
+----+-------+------+-------+------------+------+
10 rows in set (0.00 sec)

mysql>
```
### 删除表中的一条记录
- delete * form tablename where 条件
```sql
use workflow;
INSERT INTO employee (id,name,title,department,sex) VALUES (
	11,'Li',3,3,0
);
```
| id | name  | sex  | title | department | tell |
|-|-|-|-|-|-|
|1	|Crawn	|0	|1	|1|	
|2	|Jack	|0	|2	|2|	
|3	|Rose	|1	|2	|3|	
|4	|Bob	|0	|3	|2|	
|5	|Lee	|1	|4	|2|	
|6	|Liu	|1	|5	|2|	
|7	|Liang	|1	|6	|2|	
|8	|Kun	|0	|4	|3|	
|9	|Nan	|1	|5	|3|	
|10	|Wang	|0	|6	|3|	
|11	|Li	    |0	|3	|3|	

```sql
DELETE FROM employee WHERE name='Li';
SELECT * FROM employee;
RESULTS:
id	name	sex	title	department	tell
1	Crawn	0	1	1	
2	Jack	0	2	2	
3	Rose	1	2	3	
4	Bob	0	3	2	
5	Lee	1	4	2	
6	Liu	1	5	2	
7	Liang	1	6	2	
8	Kun	0	4	3	
9	Nan	1	5	3	
10	Wang	0	6	3	
```
### 更新表中的一条记录的属性值
- UPDATE 表名 set 列名=新值,... WHERE 条件//
```sql
UPDATE employee set department=1 WHERE title=1 OR title=3
<!--受影响的行: 1-->
SELECT * FROM employee;
RESULTS:
1	Crawn	0	1	1	
2	Jack	0	2	2	
3	Rose	1	2	3	
4	Bob	0	3	1	
5	Lee	1	4	2	
6	Liu	1	5	2	
7	Liang	1	6	2	
8	Kun	0	4	3	
9	Nan	1	5	3	
10	Wang	0	6	3	
```
### 查询表中是否存在条件数据
- select 字段名, ... ,字段名 from 表名或查询语句 where 条件
- select ifnull(字段名,默认值) from 表名 
- 注意关键字函数： distinct（去重）、count（计数）、DESC（降）、ASC（升）
#### 查询结果分组
-  select 字段名, ... ,字段名 from 表名或查询语句 groupby 字段名 ASC where 条件
#### 模糊查询
- select 字段 from 表名 where 字段 like '_%';//（%，_,[charlist], [^charlist], [!charlist]）
```sql
 select * from employee where name like '_o%';
3	Rose	1	2	3	
4	Bob	0	3	1	
```
#### 正则表达式
- select 字段 from 表名 where 字段 REGEXP 正则表达式
### 表连接

```sql
<!--向title注入一个employee.id不包含的值，使employee和title的id中都包含对方没有的值，便于对比不同连接的结果-->
INSERT INTO title (id,title) VALUES (22,'crawn')
select * from title;
```
![result](https://i.imgur.com/X9Aa0Zx.png)
#### 内连接
- select * from 左表 INNER JOIN 右表 on 连接条件（某一属性相等） 
```sql
select * from employee INNER JOIN title on employee.id = title.id 
select * from employee;
```

![result](https://i.imgur.com/oW7Ks7J.png)
#### 左（外）连接
- select * from 左表 LEFT （OUTER） JOIN 右表 on 连接条件（某一属性相等） //<b>注意：（OUTER）可写可不写</b>
```sql
select * from employee;
```

![result](https://i.imgur.com/pXtm4Hz.png)
#### 右（外）连接
- select * from 左表 RIGHT （OUTER） JOIN 右表 on 连接条件（某一属性相等）<b>注意：（OUTER）可写可不写</b>
```sql
select * from employee  RIGHT JOIN title on employee.id = title.id 
select * from employee;
```
![result](https://i.imgur.com/04L1biN.png)

#### 外连接（全连接）
- MySQL中没有实现外链接，替代写法：左（外）连接语句 UNION 右（外）连接语句
```sql
select * from employee LEFT OUTER JOIN title on employee.id = title.id UNION select * from employee  RIGHT OUTER JOIN title on employee.id = title.id
select * from employee;
```
![result](https://i.imgur.com/0H8Chax.png)
### 表复制
- SELECT * INTO 目标表  FROM 原表（SQLServer）
- insert into 目标表 select * from  原表 （MYSQL）
```sql
CREATE TABLE title_copy (id int(11),title VARCHAR(20)) 
insert into title_copy select id,title from  title
SELECT * FROM title_copy
```
![result](https://i.imgur.com/iEbXinw.png)
## 索引 
- （数据文件：. myd 索引文件：. MYI 表定义文件：. frm）
- 索引是一种特殊的文件(InnoDB数据表上的索引是表空间的一个组成部分)，它们包含着对数据表里所有记录的引用指针。更通俗的说，数据库索引好比是一本书前面的目录，能加快数据库的查询速度。
- 索引分为聚簇索引和非聚簇索引两种，聚簇索引是按照数据存放的<font color=#FF0000>物理位置</font>为顺序的，而非聚簇索引就不一样了；<font color=#FF0000>聚簇索引能提高多行检索的速度，而非聚簇索引对于单行的检索很快</font>.
- 要注意的是，<font color=#FF0000><b>建立太多的索引将会影响更新和插入的速度</b></font>，因为它需要<font color=#FF0000><b>同样更新每个索引文件</b></font>。对于一个经常需要更新和插入的表格，就没有必要为一个很少使用的where字句单独建立索引了，对于比较小的表，排序的开销不会很大，也没有必要建立另外的索引。
### 建立索引
- CREATE INDEX 索引名（唯一） ON 索引表 (索引键（可以是组合）)//索引可以以"数据库名_表名_字段名"的方式命名
### 删除索引
- DROP INDEX 索引名（唯一） ON 索引表 (索引键（可以是组合）)
## 视图
### 创建视图
- Create View 视图名 as 查询语句
```sql
Create View aa as SELECT * from title_copy where id < 4
```
![result](https://i.imgur.com/VpUZGQA.png)
### 更改视图
- insert into 视图 。。。//<font color=#FF0000><b>视图和原表都会执行插入操作，视图有时不显示，是因为插入的记录不是视图的条件</b></font>
- update/delete/select// <font color=#FF0000><b>需要对视图中保有的数据做更改，同时映射到原表；若针对视图操作原表有视图无的数据，则不会有任何更改</b></font>
```sql
insert into aa (id,title) VALUES(20,'King')
```
![result](https://i.imgur.com/2xtDG4u.png)
![result](https://i.imgur.com/02pwSrd.png)
### 删除视图
- DROP view 视图名;
```sql
drop view aa;
```
![result](https://i.imgur.com/VpUZGQA.png)

## 事务处理
- 1.在 MySQL 中只有使用了 Innodb 数据库引擎的数据库或表才支持事务。
- 2.事务处理可以用来维护数据库的完整性，保证成批的 SQL 语句要么全部执行，要么全部不执行。
- 3.事务用来管理 insert,update,delete 语句
- 4.事务是必须满足4个条件（ACID）：原子性（Atomicity，或称不可分割性）、一致性（Consistency）、隔离性（Isolation，又称独立性）、持久性（Durability）。
### 设定是否自定提交
- SET AUTOCOMMIT=0 禁止自动提交 
- SET AUTOCOMMIT=1 开启自动提交
- 在默认的情况下，MySQL从自动提交（autocommit）模式运行，这种模式会在每条语句执行完毕后把它作出的修改立刻提交给数据库并使之永久化。
### 事务回滚
- <b>事务的回滚正确的理解应该是，如果事务中所有sql语句执行正确则需要自己手动提交commit；否则有任何一条执行错误，需要自己提交一条rollback，这时会回滚所有操作，而不是commit会给你自动判断和回滚。</b>
### 运行事务语句及实例 
- begin;(操作语句));...;commit；//若有异常会直接执行回滚操作，退出到<br>
##### begin开始前的状态:
![result](https://i.imgur.com/7REAFLX.png)
#### 运行包含错误的事务
```sql
begin;
insert into title_copy values(7, 'abc');
insert into title_copy (id,title) values(8, 'abc');
UPDATE title_copy set title='King' WHERE id=6;
COMMIT;
```
执行select结果：<br/>
![result](https://i.imgur.com/nxRZ8pQ.png)
- 结果原因分析：
    - 运行到第3行由于title不能重复报错，下面两行没有执行，即commit没执行，因此事务没有结束，语句一执行成功并向表中添加数据；因此查询会包含
    |   7|  abc|.<br/>
##### 此时若执行commit操作：
```sql
COMMIT;
```
执行select结果：<br>
![result](https://i.imgur.com/omCD0Se.png)
##### 此时若执行rollback操作：
```sql
ROLLBACK;
```
执行select结果：<br/>
![result](https://i.imgur.com/0gSaK8Y.png)
### 运行正确的事务流程
```sql
begin;
insert into title_copy values(7, 'abc');
insert into title_copy (id,title) values(8, 'abc');
UPDATE title_copy set title='King' WHERE id=6;
COMMIT;
```
执行select结果：<br/>
![result](https://i.imgur.com/vxQDcA3.png)
## 数据库备份
### 导出数据库或表
- mysqldump -u用户名 -h用户IP -p 数据库名 或 数据库 表名> 目标文件夹\数据库名.sql
    - mysqldump -uroot -hlocalhost -p workflow > D:\workflow.sql
    - --database 数据库1 数据库2  导出多个数据库
    - --all-databases  导出所有数据库  
- 输入用户密码
### 导入数据库或表
- mysql -u root -h localhost -p 数据库名< 目标文件夹\数据库名.sql
<b>注意：备份前应先创建数据库名</b>
    - mysqldump -uroot -hlocalhost -p workflow_copy > D:\workflow.sql
- 输入用户密码
- 或 登录到数据库中，使用Source命令: source 目标文件夹\数据库名.sql
    - source D:\workflow.sql
## 日志管理
- BinLog配置
    - log-bin="C:\Mysql\log"
    - expire_logs_days=10
    - max_binlog_size=100M
    - 重启MySQL服务

- 通过变量查询可以查看配置的日志是否开启
    -show VARIABLES LIKE '%log_%';
- 查看二进制文件命令
    - SHOW BINARY LOGS;
- 查看二进制操作记录，Insert，Update语句
    - show binlog events;
- 利用BinLog恢复数据库
    - mysqlbinlog --stop-datetime="2017-04-17 22:02:00" "C:\mysql\log.000001" |mysql -u root –p

- ErrorLog配置
    - log-error=[path/[file_name]] show variables LIKE 'log_error';

- 慢日志查询配置
    - log-slow-queries[=path/[filename]] 
    - long_query_time=10
    - show variables like '%slow%';
## 同步复制
### Master配置
    [mysqld] 
    log-bin="D:/MYSQLDataBase/binlog" expire_logs_days=10 
    max_binlog_size=100M 
    server-id=1 
    binlog-do-db=test 
    binlog-ignore-db=mysql
### Slave配置
    [mysql]
    default-character-set=utf8
    log_bin="C:/MYSQLLOG/binlog"
    expire_logs_days=10
    max_binlog_size=100M

    [mysqld]
    server-id=2
    change master to master_host='192.168.1.100', 
    master_user='repl',
    master_password='123',
    master_log_file='binlog.000004', 
    master_log_pos=107;



