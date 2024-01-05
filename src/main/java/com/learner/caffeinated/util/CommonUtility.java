package com.learner.caffeinated.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CommonUtility {
	 public static String getCurrentUser() {
	        // Get the authentication object from the SecurityContextHolder
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        // Check if the user is authenticated
	        if (authentication != null && authentication.isAuthenticated()) {
	            // Retrieve the username from the authentication object
	            String username = authentication.getName();

	            return username;
	        } else {
	            return "";
	        }
	    }
}
