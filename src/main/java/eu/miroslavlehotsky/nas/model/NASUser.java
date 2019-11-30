package eu.miroslavlehotsky.nas.model;

import lombok.Data;

@Data
public class NASUser {
    private String username;
    private String password;
    private String roles[];
}
