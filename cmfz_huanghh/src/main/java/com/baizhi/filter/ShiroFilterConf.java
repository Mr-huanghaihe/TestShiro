package com.baizhi.filter;

import com.baizhi.realm.MyRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @program: untitled
 * @description: 配置shrio的核心过滤器配置
 * @author: Mr.huang
 * @create: 2019-08-13 17:07
 */

@Configuration
public class ShiroFilterConf {
    //将shiro过滤器工厂交给spring工厂
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(WebSecurityManager webSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //创建shiro过滤器工厂
        shiroFilterFactoryBean.setSecurityManager(webSecurityManager);
        HashMap<String, String> map = new HashMap<>();
        //AnonymousFilter 匿名过滤器   anon  不拦截
        //FormAuthenticationFilter 认证过滤器  authc  资源要认证通过才放行
        //配置过滤器链
        map.put("/**","authc");
        map.put("/shiro/login","anon");
        map.put("/main/main.jsp","anon");
        map.put("/admin/code","anon");
        map.put("/bootstrap/**","anon");
        map.put("/echartsjs/**","anon");
        map.put("/kindeditor/**","anon");
        map.put("/login/assets/**","anon");
        //设置自定义登陆页面的路径
        shiroFilterFactoryBean.setLoginUrl("/login/login.jsp");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    //将安全管理器交给spring工厂
    @Bean
    public WebSecurityManager getWebSecurityManager(MyRealm myRealm, EhCacheManager ehCacheManager){
        //创建安全管理器
        DefaultWebSecurityManager webSecurityManager = new DefaultWebSecurityManager();
        //将缓存给安全管理器
        webSecurityManager.setCacheManager(ehCacheManager);
        //将自定义realm给安全管理器
        webSecurityManager.setRealm(myRealm);
        return webSecurityManager;
    }

    //将缓存交给spring工厂
    @Bean
    public EhCacheManager getEhCacheManager(){
        EhCacheManager ehCacheManager = new EhCacheManager();
        return ehCacheManager;
    }

    //将自定义Realm交给spring工厂
    @Bean
    public MyRealm getMyRealm(HashedCredentialsMatcher hashedCredentialsMatcher){
        MyRealm myRealm = new MyRealm();
        //将你选择的凭证匹配器给realm
        myRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return myRealm;
    }

    //将凭证匹配器交给spring工厂
    @Bean
    public HashedCredentialsMatcher getHashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(1024);
        return hashedCredentialsMatcher;
    }
}