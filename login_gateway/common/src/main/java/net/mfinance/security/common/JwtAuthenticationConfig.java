package net.mfinance.security.common;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

/**
 * Config JWT.
 * Only one property 'shuaicj.security.jwt.secret' is mandatory.
 *
 * @author shuaicj 2017/10/18
 */
@Getter
@ToString
public class JwtAuthenticationConfig {

    @Value("${mf.security.jwt.url:/login}")
    private String url;

    @Value("${mf.security.jwt.header:Authorization}")
    private String header;

    @Value("${mf.security.jwt.prefix:Bearer}")
    private String prefix;

    @Value("${mf.security.jwt.expiration:#{60*60}}")
    private int expiration;

    //一个月
    @Value("${mf.security.jwt.expirationRefresh:#{24*60*60*30}}")
    private int expirationRefresh;

    @Value("${mf.security.jwt.secret}")
    private String secret;
}
