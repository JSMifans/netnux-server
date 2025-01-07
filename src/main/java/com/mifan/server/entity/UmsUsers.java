package com.mifan.server.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 存储用户信息
 * </p>
 *
 * @author Milan
 * @since 2024-12-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ums_users")
public class UmsUsers implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码哈希
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordHash;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 用户状态，1表示激活，0表示禁用
     */
    private Integer status;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 流量
     */
    private Integer flow;

    /**
     * 代理商
     */
    private Integer parentId;

    @TableField(exist = false)
    private String parentName;

    /**
     * 余额
     */
    private Integer balance;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    /**
     * 角色名称
     */
    @TableField(exist = false)
    private String roleName;
}
