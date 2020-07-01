package springdemo02.mybatisdemo.securityPackage.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 1、引入SpringSecurity；
 * 2、编写SpringSecurity的配置类；
 * 		@EnableWebSecurity   extends WebSecurityConfigurerAdapter
 * 3、控制请求的访问权限：
 * 		configure(HttpSecurity http) {
 * 		 	http.authorizeRequests().antMatchers("/").permitAll()
 * 		 		.antMatchers("/level1/**").hasRole("VIP1")
 * 		}
 * 4、定义认证规则：
 * 		configure(AuthenticationManagerBuilder auth){
 * 		 	auth.inMemoryAuthentication()
 * 		 		.withUser("zhangsan").password("123456").roles("VIP1","VIP2")
 * 		}
 * 5、开启自动配置的登陆功能：
 * 		configure(HttpSecurity http){
 * 		 	http.formLogin();
 * 		}
 * 6、注销：http.logout();
 * 7、记住我：Remeberme()；
 */

//定义授权规则
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        //定制请求的授权规则
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");

        //开启自动配置的登陆功能，效果，如果没有登陆，没有权限就会来到登陆页面
        http.formLogin().usernameParameter("user").passwordParameter("pwd")
                .loginPage("/userlogin");//原本是.loginPage("login")
        //1、/login来到登陆页
        //2、重定向到/login?error表示登陆失败
        //3、更多详细规定
        //4、默认post形式的 /login代表处理登陆
        //5、一但定制loginPage；那么 loginPage的post请求就是登陆

        //开启自动配置的注销功能。
        http.logout().logoutSuccessUrl("/");//注销成功以后来到首页
        //1、访问 /logout 表示用户注销，清空session
        //2、注销成功会返回 /login?logout 页面；

        //开启记住我功能
        http.rememberMe().rememberMeParameter("remeber");
        //登陆成功以后，将cookie发给浏览器保存，以后访问页面带上这个cookie，只要通过检查就可以免登录
        //点击注销会删除cookie
    }

    //定义认证规则
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /**
         * 两种方法都行，建议使用第一种配置，使用BCryptPasswordEncoder来加密
         */
        auth.inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("zhangsan")
                .password(new BCryptPasswordEncoder().encode("123456")).roles("VIP1","VIP2")

                .and().withUser("lisi")
                .password(new BCryptPasswordEncoder().encode("123456")).roles("VIP1","VIP3")

                .and().withUser("wangwu")
                .password(new BCryptPasswordEncoder().encode("123456")).roles("VIP2","VIP3");


//        auth.inMemoryAuthentication() //认证信息存储到内存中
//                .passwordEncoder(passwordEncoder())
//                //必须使用一种加密方式加密
//                .withUser("zhangsan").password(passwordEncoder().encode("123456")).roles("VIP1","VIP2")
//                .and()
//                .withUser("lisi").password(passwordEncoder().encode("123456")).roles("VIP1","VIP3")
//                .and()
//                .withUser("wangwu").password(passwordEncoder().encode("123456")).roles("VIP2","VIP3");
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
