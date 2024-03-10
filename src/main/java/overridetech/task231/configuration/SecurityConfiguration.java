package overridetech.task231.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    @Qualifier("userDetailsSerivceImpl")
    private UserDetailsService userDetailsService;
    @Autowired
    private SuccessUserHandler successUserHandler;

//    @Value("${spring.websecurity.debug:false}")
//    boolean webSecurityDebug;
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.debug(webSecurityDebug);
//    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder authN) throws Exception {
        authN.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/userpage").hasAnyRole("user", "admin")
                .antMatchers("/start").hasRole("admin")
                .and().formLogin()
                .successHandler(successUserHandler)
                .and().logout();
    }

    @Bean
    public static PasswordEncoder getPasswordEncoder() {
        return new StandardPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
        return daoAuthenticationProvider;
    }


}
