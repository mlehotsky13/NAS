package eu.miroslavlehotsky.nas.model;

import java.util.Set;

import eu.miroslavlehotsky.nas.model.cst.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NASUser {
	private String username;
	private String password;
	private Set<RoleType> roles;
}