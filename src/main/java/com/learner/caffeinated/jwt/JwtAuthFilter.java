//package com.learner.caffeinated.jwt;
//
//import java.io.IOException;
//
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthFilter extends OncePerRequestFilter {
//	private final UserDetailsService userDetailsService;
//	private final JwtUtil jwtUtil;
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		final String authHeader = request.getHeader("AUTHORIZATION");
//		final String userEmail;
//		final String jwtToken;
//		if (authHeader == null || !authHeader.startsWith("Bearer")) {
//			filterChain.doFilter(request, response);
//			return;
//		}
//		jwtToken = authHeader.substring(7);
//		userEmail = jwtUtil.extractUsername(jwtToken);
//		if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//			UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
//			final boolean isTokenValid=false;
//			if (jwtUtil.isTo) {
//				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
//						null, userDetails.getAuthorities());
//				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//				SecurityContextHolder.getContext().setAuthentication(authToken);
//			}
//		}
//		filterChain.doFilter(request, response);
//	}
//
//}
