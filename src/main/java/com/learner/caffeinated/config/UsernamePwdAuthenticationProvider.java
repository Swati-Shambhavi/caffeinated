package com.learner.caffeinated.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.learner.caffeinated.entity.User;
import com.learner.caffeinated.repo.UserRepository;

import lombok.extern.slf4j.Slf4j;
@Component
@Slf4j
public class UsernamePwdAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userName = authentication.getName();
		String pswd = authentication.getCredentials().toString();
		List<User> users = userRepository.findByEmail(userName);
		if (users.size() > 0) {
			if (passwordEncoder.matches(pswd, users.get(0).getPassword())) {
				 List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
				 grantedAuthorities.add(new SimpleGrantedAuthority(users.get(0).getRole()));
				 return new UsernamePasswordAuthenticationToken(userName, pswd, grantedAuthorities);
//				 return new UsernamePasswordAuthenticationToken(userName, pswd, getGrantedAuthorities(users.get(0).getAuthorities()));
            } else {
                throw new BadCredentialsException("Invalid password!");
            }
        }else {
            throw new BadCredentialsException("No user registered with this details!");
        }
	}

	 /**private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities) {
	        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
	        for (Authority authority : authorities) {
	            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
	        }
	        log.info("grantedAuthorities------"+grantedAuthorities);
	        return grantedAuthorities;
	    }**/

	    @Override
	    public boolean supports(Class<?> authentication) {
	        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	    }


}
