package com.gzcr.sso.objects.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author redhawk
 * @since 2019-11-21
 */
@Data
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 登录密码
     */
    @NotBlank(message = "密码不能为null.")
    private String password;

    /**
     * 用户真实姓名
     */
    private String name;

    /**
     * 用户身份证号
     */
    private Integer idCardNum;

    /**
     * 用户状态：0:正常状态,1：用户被锁定
     */
    private String state;

    /**
     * 手机号码
     */

    @NotBlank(message = "phone code cant not blank null! ")
    private String phone;

    /**
     * 登陆次数
     */
    private Integer loginNumber;

    /**
     * 备注
     */
    private String remark;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer delFlag;

    /**
     * 状态(是否可用)
     */
    private Integer status;

    /**
     * 系统标识
     */
    private Integer sysFlag;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;



}
