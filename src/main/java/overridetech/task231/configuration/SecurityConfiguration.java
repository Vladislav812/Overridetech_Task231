package overridetech.task231.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import overridetech.task231.repository.UserRepository;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    UserRepository userRepository;

    @Autowired
    @Qualifier("userDetailsSerivceImpl")
    private UserDetailsService userDetailsService;
    @Autowired
    private SuccessUserHandler successUserHandler;

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder authN) throws Exception {
        authN.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/user").hasAnyRole("user", "admin")
                .antMatchers("/admin/**").hasRole("admin")
                .and().formLogin()
                .successHandler(successUserHandler)
                .and().logout();
    }

    @Bean
    public static PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
