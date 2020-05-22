package io.niufen.common.entity;

import lombok.Data;

import java.util.Date;

/**
 * 基础实体类
 * @author haijun.zhang
 * @date 2020/5/9
 * @time 13:16
 */
@Data
public class BaseEntity {

    private Long id;

    private Long createUserId;

    private String createUserName;

    private Date createTime;

    private Long updateUserId;

    private String updateUserName;

    private Date updateTime;

}
