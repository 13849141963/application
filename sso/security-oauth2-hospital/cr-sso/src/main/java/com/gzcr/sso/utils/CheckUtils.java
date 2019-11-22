package com.gzcr.sso.utils;/**
 * @author RedHawk
 * @create 2019-11-20 12:12
 */

/*
//需要添加这个依赖, 这个Utils才能使用
   <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.4</version>
        </dependency>

 */

/*
使用方法
   CheckUtils.checkEmpty("这里是需要验证的参数","参数名称");
        String id = "";
        CheckUtils.checkEmpty(id,"父类ID");
 */

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * <Description> <br>
 *
 * @author redhawk<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2019/11/20 12:12 <br>
 * @see com.gzcr.sso.utils <br>
 */
public class CheckUtils {
    public static void checkNull(Object o) {
        if (o == null) {
            throw new ServiceException();
        }
    }

    public static void checkNull(String msg) {
        throw new ServiceException(msg + "不能为null");
    }


    public static void checkNull(Object o, String entityName) {
        if (o == null) {
            throw new ServiceException(entityName + "cant not blank null!");
        }
    }

    public static void checkEmpty(String o, String entityName) {
        if (StringUtils.isEmpty(o)) {
            throw new ServiceException(entityName + "cant not blank null!");
        }
    }

    public static void checkEmpty(Collection c, String entityName) {
        if (c == null || c.isEmpty()) {
            throw new ServiceException(entityName + "cant not blank null!");
        }
    }
}

class ServiceException extends RuntimeException {
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
