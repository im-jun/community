# 码农论坛

## 一、文档
- [Github 授权登录](https://docs.github.com/en/developers/apps/building-oauth-apps/)
- [okhttp](https://square.github.io/okhttp/)
- [flyway](https://flywaydb.org/documentation/)

## 二、工具
- [Visual Paradigm](https://www.visual-paradigm.com/cn/download/community.jsp)

## SQL脚本
```sql
-- user 表
create table USER
(
    ID           INT auto_increment,
    ACCOUNT_ID   VARCHAR(100),
    NAME         VARCHAR(50),
    TOKEN        CHAR(36),
    GMT_CREATE   BIGINT,
    GMT_MODIFIED BIGINT,
    constraint USER_PK
        primary key (ID)
);
```