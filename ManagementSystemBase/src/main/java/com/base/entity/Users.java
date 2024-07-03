package com.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author psl
 * @since 2024-07-03
 */
@Getter
@Setter
public class Users {

    /**
     * 用户Id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户性别
     */
    private String userSex;

    /**
     * 用户生日
     */
    private Timestamp userBirch;

    /**
     * 用户电话
     */
    private String userPhone;

    /**
     * 密码
     */
    private String password;

    /**
     * 创建日期
     */
    private Timestamp createDate;

    /**
     * 用户邮箱
     */
    private String userEmail;
}
