package eu.miroslavlehotsky.nas.service;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import eu.miroslavlehotsky.nas.model.NASUser;
import eu.miroslavlehotsky.nas.model.cst.RoleType;

@Service
public class NASUserDetailsService implements UserDetailsService {

	@Autowired
	private UserFileSystemService userFileSystemService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<NASUser> user = userFileSystemService.getUserByName(username);
		return user.map(u -> User//
				.withUsername(u.getUsername())//
				.password(u.getPassword())//
				.roles(getStringRoles(u.getRoles()))//
				.build())//
				.orElseThrow(() -> new UsernameNotFoundException("Could not find user " + username + "."));
	}
	
	private String[] getStringRoles(RoleType roles[]) {
		return Arrays.stream(roles).map(RoleType::name).collect(Collectors.toList()).toArray(new String[roles.length]);
	}
}