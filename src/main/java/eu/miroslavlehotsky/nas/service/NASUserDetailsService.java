package eu.miroslavlehotsky.nas.service;

import java.util.Optional;
import java.util.Set;
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

	private String[] getStringRoles(Set<RoleType> roles) {
		return roles.stream().map(RoleType::name).collect(Collectors.toList()).toArray(new String[roles.size()]);
	}
}