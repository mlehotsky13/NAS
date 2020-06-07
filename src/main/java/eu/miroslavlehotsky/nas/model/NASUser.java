package eu.miroslavlehotsky.nas.model;

import eu.miroslavlehotsky.nas.model.cst.RoleType;
import lombok.Data;

@Data
public class NASUser {
	private String username;
	private String password;
	private RoleType roles[];
}