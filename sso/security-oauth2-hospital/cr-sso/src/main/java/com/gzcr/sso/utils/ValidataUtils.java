package com.gzcr.sso.utils;/**
 * @author RedHawk
 * @create 2019-11-20 14:20
 */

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * <Description> <br>
 *
 * @author redhawk<br>
 * @taskId:   <br>
 * @version 1.0<br>
 * @createDate 2019/11/20 14:20 <br>
 * @see com.gzcr.sso.utils <br>
 */
public class ValidataUtils {


    //检查登陆名称是否有空格

    /**
     * 检查手机号是否正确
     *
     * @param mobiles
     * @return
     */
    public static boolean isPhoneNumber(String mobiles) {
        String formatePhone = StringUtils.trim(mobiles);
        String regex = "^((13[0-9])|(14[5|7])|(15[^4])|(17[0|3|5-8])|(18[0-9])|166|198|199|147)\\d{8}$";
        return Pattern.matches(regex, formatePhone);
    }

    public static boolean isIdNo(String idNo) {
        String formateIdNo = StringUtils.trim(idNo);
        String regex = "(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)";
        return Pattern.matches(formateIdNo, regex);
    }
}
