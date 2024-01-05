//package com.learner.caffeinated.config;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.learner.caffeinated.model.User;
//import com.learner.caffeinated.repo.UserRepository;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Service
//@Slf4j 
//public class CaffeinatedUserDetailsService implements UserDetailsService {
//	@Autowired
//	private UserRepository userRepository;
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		String userName,password;
//		List<GrantedAuthority> authorities=new ArrayList<>();
//			List<User> user = userRepository.findByEmail(username);
//			if(user.size() == 0) {
//				throw new UsernameNotFoundException("User Detail not found");
//			}
//			log.info("loadUserByUsername",user.get(0).toString());
//			userName = user.get(0).getEmail();
//			password = user.get(0).getPassword();
//			authorities.add(new SimpleGrantedAuthority(user.get(0).getRole()));
//		return new org.springframework.security.core.userdetails.User(userName, password,authorities);
//	}
//
//}
