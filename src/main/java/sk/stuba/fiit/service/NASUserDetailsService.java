package sk.stuba.fiit.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import sk.stuba.fiit.model.NASUser;

public class NASUserDetailsService implements UserDetailsService {

    private UserFileSystemService userFileSystemService = new UserFileSystemService();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<NASUser> user = userFileSystemService.getUserByName(username);
        return user.map(v -> User.withDefaultPasswordEncoder()//
                .username(v.getUsername())//
                .password(v.getPassword())//
                .roles(v.getRoles())//
                .build())//
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user " + username + "."));
    }
}
