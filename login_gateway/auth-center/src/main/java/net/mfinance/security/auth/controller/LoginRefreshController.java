package net.mfinance.security.auth.controller;

import com.alibaba.fastjson.JSONObject;
import net.mfinance.security.auth.JwtAuthenticationConfig;
import net.mfinance.security.auth.domain.SysUser;
import net.mfinance.security.auth.repository.SysUserRepository;
import net.mfinance.security.auth.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginRefreshController {


    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    JwtAuthenticationConfig jwtAuthenticationConfig;
    @Autowired
    SysUserRepository sysUserRepository;

    @GetMapping("/login/refresh")
    public String freshToken(@RequestHeader String authorization, String refreshToken, HttpServletResponse httpResponse){
        JSONObject jsonObject = new JSONObject();
        if(StringUtils.isEmpty(authorization) || StringUtils.isEmpty(refreshToken)){
            httpResponse.setStatus(403);
            jsonObject.put("code",403);
            jsonObject.put("message","authorization or refreshToken is empty");
            return jsonObject.toJSONString();
        }
        //校验access token 是否过期
        if (authorization.startsWith(jwtAuthenticationConfig.getPrefix() + " ")) {
            authorization = authorization.replace(jwtAuthenticationConfig.getPrefix() + " ", "");
            if(jwtUtils.isTokenExpired(authorization)){
                if(jwtUtils.isTokenExpired(refreshToken)){
                    httpResponse.setStatus(401);
                    jsonObject.put("code",401);
                    jsonObject.put("message","refreshToken is expired");
                    return jsonObject.toJSONString();
                }
                //刷新token 和 延长 refresh token
                String username =  jwtUtils.getUsernameFromToken(refreshToken);
                SysUser sysUser = sysUserRepository.findByUsername(username);
                jwtUtils.refreshToken(refreshToken,username);
                httpResponse.addHeader(jwtAuthenticationConfig.getHeader(), jwtAuthenticationConfig.getPrefix() + " " + jwtUtils.generateAccessToken(sysUser));
                jsonObject.put("code",200);
                jsonObject.put("refresh_token",refreshToken);
                return jsonObject.toJSONString();
            }else{
                httpResponse.setStatus(405);
                jsonObject.put("code",405);
                jsonObject.put("message","authorization not expired");
                return jsonObject.toJSONString();
            }
        }
        return null;
    }




}
