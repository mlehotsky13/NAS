package eu.miroslavlehotsky.nas.service;

import java.util.Optional;
import java.util.Set;

import eu.miroslavlehotsky.nas.model.NASUser;

public interface UserFileSystemService {

	/**
	 * Get user object based on its name
	 * 
	 * @param username of user
	 * @return user object
	 */
	Optional<NASUser> getUserByName(String username);

	/**
	 * Get all users
	 * @return all users
	 */
	Set<NASUser> getAllUsers();
}