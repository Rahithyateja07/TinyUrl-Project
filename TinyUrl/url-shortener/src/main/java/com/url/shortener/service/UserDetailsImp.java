package com.url.shortener.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.url.shortener.models.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDetailsImp implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String username;
	private String email;
	private String password;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	

	public UserDetailsImp(Long id, String username, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}
	
	public static UserDetailsImp build(User user) {
		GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
		return new UserDetailsImp(
				user.getId(),
				user.getUsername(),
				user.getEmail(),
				user.getPassword(),
				Collections.singletonList(authority)
				);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

}
