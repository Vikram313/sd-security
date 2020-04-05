package com.sd.auth.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sd.auth.dto.UserDTO;

/**
 * 
 * @author Vikram
 *
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Map<String, String> params= new HashMap<>();
		params.put("userName", username);
		
		UserDTO userDetails = restTemplate.getForObject("http://localhost:8102/sdapi/v1/user/{userName}", UserDTO.class, params);
		if (userDetails != null) {
			return new User(userDetails.getUserName(), userDetails.getPassword(),
					AuthorityUtils.createAuthorityList("ROLE_USER"));
		}
		return null;
		/*userDetails.getUserName();

		return usersRepository.findByName(username)
				.map(user -> new User(user.getName(), user.getPassword(),
						AuthorityUtils.createAuthorityList("ROLE_USER")))
				.orElseThrow(() -> new UsernameNotFoundException("Could not find " + username));*/
	}
}
