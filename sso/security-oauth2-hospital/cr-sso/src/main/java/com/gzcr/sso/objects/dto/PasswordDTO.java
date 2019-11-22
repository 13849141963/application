package com.gzcr.sso.objects.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "密码模型")
@Data
public class PasswordDTO {

    @ApiModelProperty(value = "用户id",required = false)
    @NotBlank(message = "用户id不能为null")
    private String id;

    @ApiModelProperty(value = "旧密码",required = true)
    @NotBlank(message = "旧密码不能为null")
    private String oldPassword;

    @ApiModelProperty(value = "新密码",required = true)
    @NotBlank(message = "新密码不能为null")
    private String newPassword;

    @ApiModelProperty(value = "手机号码",required = false)
    @NotBlank(message = "手机号码不能为null")
    private String phone;


}
