package kr.hs.entrydsm.rollsroyce.global.security.auth;

import java.util.Collection;
import java.util.Collections;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@AllArgsConstructor
public class AuthDetails implements UserDetails {

	private final String receiptCode;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return receiptCode;
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
