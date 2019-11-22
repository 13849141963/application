package com.zy.server.config;

import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: application
 * @description: 撤销访问令牌的控制器-目的：就是为了注销时候删除有效令牌
 * @author: 狗蛋
 * @create: 2019-11-03 22:04
 */
@RestController
public class BackOutAccessTokenController {


    /***
     * 客户端发请求到此接口进行单点退出，清楚当前用户，并清楚cookie，access_token
     * @param request
     * @param response
     * 单点退出的两种方案
     * https://blog.csdn.net/weixin_34401479/article/details/88899395
     */
    @RequestMapping("/exit")
    public void exit(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, null, null);
        try {
            response.sendRedirect(request.getHeader("referer"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    /***
     * 一下配置暂且未用到
     */
    //为了使令牌无效，我们将使用ConsumerTokenServices接口中的revokeToken()
    @Resource(name="tokenServices")
    ConsumerTokenServices tokenServices;

    /***
     * 删除access_token令牌
     * @param token
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/tokens/revoke")
    @ResponseBody
    public String revokeToken(String token) {
        tokenServices.revokeToken(token);
        return token;
    }
}