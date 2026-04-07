package dbspro2.utulek.security;

import dbspro2.utulek.model.Uzivatel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UzivatelDetails implements UserDetails {

    private final Uzivatel uzivatel;

    public UzivatelDetails(Uzivatel uzivatel) {
        this.uzivatel = uzivatel;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(uzivatel.getRole().getNazev()));
    }

    @Override
    public String getPassword() {
        return uzivatel.getHesloHash();
    }

    @Override
    public String getUsername() {
        return uzivatel.getEmail();
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