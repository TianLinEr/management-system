package com.base.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserVO implements Serializable {

    /**
     * 用户Id
     */
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
    private String userBirch;

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
    private String createDate;

    /**
     * 用户邮箱
     */
    private String userEmail;

    /**
     * 用户权限
     */
    private String userType;

    /**
     * 用户类别
     */
    private String userCategory;
}
