package com.codegym.model.dto;


import com.codegym.model.account.Account;
import com.codegym.model.account.Role;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class AccountPrincipal implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String birthday;
    private String avatar;
    private String fullName;
    private String address;
    private String favorite;
    private Collection<? extends GrantedAuthority> roles;

    public AccountPrincipal(Long id, String username, String password, String email, String phone, String birthday, String avatar, String fullName, String address, String favorite, Collection<? extends GrantedAuthority> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.avatar = avatar;
        this.fullName = fullName;
        this.address = address;
        this.favorite = favorite;
        this.roles = roles;
    }

    public static AccountPrincipal build(Account account){
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(Role role: account.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new AccountPrincipal(account.getId(), account.getUsername(), account.getPassword(), account.getEmail(), account.getPhone(), account.getBirthday(), account.getAvatar(), account.getFullName(), account.getAddress(), account.getFavorite(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
