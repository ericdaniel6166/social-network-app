package com.example.socialnetworkapp.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

    //    private final String id;
    private final String name;
    //    private final String token;
    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public String getPassword() {
        return null;
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
