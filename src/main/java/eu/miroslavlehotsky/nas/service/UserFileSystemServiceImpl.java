package eu.miroslavlehotsky.nas.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import eu.miroslavlehotsky.nas.model.NASUser;

@Service
public class UserFileSystemServiceImpl implements UserFileSystemService {

	private static final ObjectMapper om = new ObjectMapper();

	@Override
	public Optional<NASUser> getUserByName(String username) {
		return getAllUsers().stream().filter(u -> username.equals(u.getUsername())).findFirst();
	}

	@Override
	public Set<NASUser> getAllUsers() {
		Set<NASUser> users = new HashSet<>();

		ArrayNode usersJson = loadUsersJson();
		for (JsonNode user : usersJson) {
			try {
				users.add(om.treeToValue(user, NASUser.class));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}

		return users;
	}

	@Override
	public void deleteUser(String username) {
		try {
			Set<NASUser> users = getAllUsers().stream().filter(user -> !username.equals(user.getUsername()))
					.collect(Collectors.toSet());
			saveUsersJson(users);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private void saveUsersJson(Set<NASUser> users) throws URISyntaxException {
		ArrayNode usersJson = om.createArrayNode();
		users.stream().forEach(usersJson::addPOJO);

		try (OutputStream os = Files.newOutputStream(Paths.get("users.json"))) {
			om.writeValue(os, users);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ArrayNode loadUsersJson() {
		ArrayNode usersJson = om.createArrayNode();

		try (InputStream is = Files.newInputStream(Paths.get("users.json"))) {
			usersJson = (ArrayNode) om.readTree(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return usersJson;
	}
}