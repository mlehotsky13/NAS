package eu.miroslavlehotsky.nas.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import eu.miroslavlehotsky.nas.model.NASUser;

public class NASUserDetailsService implements UserDetailsService {

	private UserFileSystemService userFileSystemService = new UserFileSystemService();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<NASUser> user = userFileSystemService.getUserByName(username);
		return user.map(u -> User//
				.withUsername(u.getUsername())//
				.password(u.getPassword())//
				.roles(u.getRoles())//
				.build())//
				.orElseThrow(() -> new UsernameNotFoundException("Could not find user " + username + "."));
	}
}