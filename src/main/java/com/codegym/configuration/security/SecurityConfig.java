package com.codegym.configuration.security;

import com.codegym.configuration.custom.CustomAccessDeniedHandler;
import com.codegym.configuration.custom.RestAuthenticationEntryPoint;
import com.codegym.configuration.filter.JwtAuthenticationFilter;
import com.codegym.model.account.Account;
import com.codegym.model.account.Privacy;
import com.codegym.model.account.Role;
import com.codegym.model.enumeration.EFriendStatus;
import com.codegym.model.friend.FriendStatus;
import com.codegym.service.account.IAccountService;
import com.codegym.service.friendStatus.IFriendStatusService;
import com.codegym.service.privacy.IPrivacyService;
import com.codegym.service.role.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private IAccountService accountService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IFriendStatusService friendStatusService;

    @Autowired
    private IPrivacyService privacyService;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public RestAuthenticationEntryPoint restServicesEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @PostConstruct
    public void init() {
        List<Account> accounts = (List<Account>) accountService.findAll();
        List<Role> roleList = (List<Role>) roleService.findAll();
        if (roleList.isEmpty()) {
            Role roleAdmin = new Role();
            roleAdmin.setId(1L);
            roleAdmin.setName("ROLE_ADMIN");
            roleService.save(roleAdmin);
            Role roleUser = new Role();
            roleUser.setId(2L);
            roleUser.setName("ROLE_USER");
            roleService.save(roleUser);
        }
        if (accounts.isEmpty()) {
            Account admin = new Account();
            Set<Role> roles = new HashSet<>();
            roles.add(new Role(1L, "ROLE_ADMIN"));
            admin.setUsername("admin");
            admin.setEmail("dangminhtuan.arwen@gmail.com");
            admin.setPassword("123456");
            admin.setPhone("0999999999");
            admin.setBirthday("01/01/2022");
            admin.setRoles(roles);
            accountService.save(admin);
        }
       List<FriendStatus> friendStatuses = (List<FriendStatus>) friendStatusService.findAll();
        if (friendStatuses.isEmpty()) {
            FriendStatus status1 = new FriendStatus();
            status1.setId(1L);
            status1.setStatus(EFriendStatus.FRIEND);
            friendStatusService.save(status1);
            FriendStatus status2 = new FriendStatus();
            status2.setId(2L);
            status2.setStatus(EFriendStatus.PENDING);
            friendStatusService.save(status2);
            FriendStatus status3 = new FriendStatus();
            status3.setId(3L);
            status3.setStatus(EFriendStatus.GUEST);
            friendStatusService.save(status3);
        }

        List<Privacy> pries = (List<Privacy>) privacyService.findAll();
        if (pries.isEmpty()){
            Privacy privacy1 = new Privacy();
            privacy1.setId(1L);
            privacy1.setName("public");
            privacyService.save(privacy1);
            Privacy privacy2 = new Privacy();
            privacy2.setId(2L);
            privacy2.setName("friend-only");
            privacyService.save(privacy2);
            Privacy privacy3 = new Privacy();
            privacy3.setId(3L);
            privacy3.setName("only-me");
            privacyService.save(privacy3);
        }
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/**");
        http.httpBasic().authenticationEntryPoint(restServicesEntryPoint());
        http.authorizeRequests()
                .antMatchers("/","/ws/**","/status/public/**", "/relations/friends/**", "/login", "/status/account/**", "/accounts/register", "/accounts/user","/profile/**","/privacy/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors();
    }
}