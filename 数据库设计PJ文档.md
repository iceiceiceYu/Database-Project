<center>
  Database Design Project
</center>

> 18302010061 陶紫信；18302010018 俞哲轩

[TOC]

## E-R图

![Project E-R](Project E-R.png)

## 数据库表结构说明

### staff - 医院职工

```sql
-- Table staff
create table staff
(
    id       bigint       not null -- staff id (primary key)
        primary key,
    birth    varchar(255) null, -- birthday of the staff
    name     varchar(255) null, -- name of the staff
    password varchar(255) null, -- password of the account
    section  varchar(255) null, -- the section of the staff (mild, severe, critical or backup)
    type     varchar(255) null, -- the vocation of the staff (doctor, chief nurse, ward nurse or emergency nurse)
    username varchar(255) null, -- username of the account (need to be unique)
    constraint unique_user
        unique (username)
);
```

### patient - 病人

```sql
-- Table patient
create table patient
(
    id          bigint       not null -- patient id (primary key)
        primary key,
    age         int          not null, -- age of the patient
    gender      varchar(255) null, -- gender of the patient (male or female)
    level       varchar(255) null, -- level of the illness (mild, severe or critical)
    name        varchar(255) null, -- name of the patient
    quarantined bit          not null, -- be quarantined or not
    section     varchar(255) null, -- section of the illness (mild, severe or critical)
    sickbed     int          not null, -- sickbed number of the patient
    status      int          not null, -- living status of the patient (0: in hospital, 1: recover and discharge, -1: die of the illness)
    ward_name   varchar(255) null, -- which ward the patient is staying
    ward_nurse  varchar(255) null -- which ward nurse takes care of the patient
);
```

### section - 治疗区域

```sql
-- Table section
create table section
(
    id          bigint       not null -- section id (primary key)
        primary key,
    chief_nurse varchar(255) null, -- chief nurse of the section
    doctor      varchar(255) null, -- doctor of the section
    level       varchar(255) null -- level of the section (mild, severe or critical)
);

-- Table section_ward_nurses
create table section_ward_nurses
(
    section_id  bigint       not null, -- section id (foreign key references section (id))
    ward_nurses varchar(255) null, -- ward nurses of the section (a list)
    constraint list_ward_nurses
        foreign key (section_id) references section (id)
);

-- Table section_ward
create table section_wards
(
    section_id bigint       not null, -- section id (foreign key references section(id))
    wards      varchar(255) null, -- wards of the section (a list)
    constraint list_wards
        foreign key (section_id) references section (id)
);
```

### ward - 病房

```sql
-- Table ward
create table ward
(
    id       bigint       not null -- ward id (primary key)
        primary key,
    capacity int          not null, -- capacity of the ward (4 for mild, 2 for severe and 1 for critical)
    level    varchar(255) null, -- level of the ward (mild, severe or critical)
    name     varchar(255) null, -- name of the ward (need to be unique)
    constraint unique_ward_name
        unique (name)
);

-- Table ward_patients
create table ward_patients
(
    ward_id  bigint       not null, -- ward id (foreign key references ward (id))
    patients varchar(255) null, -- patients in the ward (a list)
    constraint list_patients
        foreign key (ward_id) references ward (id)
);

-- Table ward_sickbeds
create table ward_sickbeds
(
    ward_id  bigint not null, -- ward id (foreign key references ward (id))
    sickbeds int    null, -- sickbeds occupied by the patients in the ward (a list)
    constraint list_sickbeds
        foreign key (ward_id) references ward (id)
);
```

### report - 核酸检测报告

```sql
-- Table report
create table report
(
    id           bigint       not null -- report id (primary key)
        primary key,
    date         varchar(255) null, -- date of the test
    doctor       varchar(255) null, -- doctor who does the test
    level        varchar(255) null, -- level of the illness (mild, severe or critical)
    patient_id   bigint       null, -- id of the patient
    patient_name varchar(255) null, -- name of the patient
    positive     bit          not null -- is positive or negative
);
```

### daily_info - 每日信息登记

```sql
-- Table daily_info
create table daily_info
(
    id           bigint       not null -- daily_info id (primay key)
        primary key,
    date         varchar(255) null, -- date of the record
    patient_id   bigint       null, -- id of the patient
    patient_name varchar(255) null, -- name of the patient
    positive     bit          not null, -- is positive or negative
    symptom      varchar(255) null, -- symtom of the patient
    temperature  double       not null, -- temperature of the patient
    ward_nurse   varchar(255) null -- ward nurse who records the info
);
```

### message - 自动消息提示

```sql
-- Table message
create table message
(
    id           bigint       not null -- message id (primary key)
        primary key,
    message_type int          not null, -- message type (1: new patient transfering into the section, 2: patient satisfying the discharge conditions)
    patient_id   bigint       null, -- id of the patient
    patient_name varchar(255) null, -- name of the patient
    staff        varchar(255) null -- staff who receives the message
);
```

## 核心功能的SQL语句说明

修改自己的账户信息

```sql
update staff set birth = '1990-01-01' and name='newName' where usename = 'mDoctor'
```

验证账户密码（为空则错误）

```sql
select * from staff where username='mDoctor' and password = '123456'
```

根据医生用户名获取管理的病人信息

```sql
select patient.*
from patient natural join staff on section
where staff.username = 'mDoctor' and staff.type = 'doctor';
```

根据相应删选条件选择病人

```sql
--处于轻症区--
select * from patient where section = 'mild';
--正在隔离区等待--
select * from patient where section = NULL and wardName = NULL;
--等待转入其他病房--
select * from patient where level != section;
--已经康复的病人--
select * from patient where status = 1;
```

医生更改病人病情评级

```sql
update patient set level = 'mild' where id = 10;
```

医生更改病人生存状态

```sql
update patient set satus = -1 where id = 10;
```

医生允许病人出院

```sql
update patient set satus = 1 where id = 10;
```

根据医生用户名获取病区内病房护士信息

```sql
select s.*
from staff natural join (staff as s) on section
where staff.type='doctor' and staff.username='mDoctor' and s.type='ward nurse';
```

医生新增核酸检测单

```sql
insert into report(date,doctor,level,patient_id,patient_name,positive)
values('2020-01-02','mDoctor','mild',20,'张三'，1)
```

寻找空闲的护士

```sql
select * from staff where type='nurse' and section = NULL;
```

护士长新增护士

```sql
update staff set section = 'mild' where id = 5;
```

护士每日信息登记

```sql
insert into daily_info( date,patient_id,patient_name,positive,symptom,temperature,ward_nurse) values(2020-01-01,10,'张三',1,'发烧','38.1','mWard1');
```

急诊护士新增病人

```sql
insert into patient(age,gender,level,namequarantined,section,sickbed    status,ward_name,ward_nurse) values(20,'female','mild',1,'mild',null,1,null,null);
```

获取新消息

```sql
select * from message where staff = 'mDoctor';
```

