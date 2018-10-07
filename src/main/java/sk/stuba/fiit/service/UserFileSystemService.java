package sk.stuba.fiit.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import sk.stuba.fiit.model.NASUser;

public class UserFileSystemService {

    private static final ObjectMapper om = new ObjectMapper();

    public Optional<NASUser> getUserByName(String username) {
        return getUsers().stream().filter(u -> username.equals(u.getUsername())).findFirst();
    }

    private Set<NASUser> getUsers() {
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

    private ArrayNode loadUsersJson() {
        ArrayNode usersJson = om.createArrayNode();

        try (InputStream is = new FileInputStream("src/main/resources/users.json")) {
            usersJson = (ArrayNode) om.readTree(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return usersJson;
    }
}
