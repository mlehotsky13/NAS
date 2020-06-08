package eu.miroslavlehotsky.nas.controller;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import eu.miroslavlehotsky.nas.model.cst.RoleType;
import eu.miroslavlehotsky.nas.service.UserFileSystemService;

@Controller
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private UserFileSystemService userFileSystemService;

	@GetMapping
	public String getUsersPage(Model model) {
		model.addAttribute("users", userFileSystemService.getAllUsers());
		model.addAttribute("roles", Arrays.asList(RoleType.values()));

		return "users";
	}

	@GetMapping("/deleteUser")
	public String deleteUser(@RequestParam String username) {
		userFileSystemService.deleteUser(username);

		return "redirect:/users";
	}

	@PostMapping("/editUser")
	public String editUser(@RequestParam String username, @RequestParam Set<String> userroles) {
		Set<RoleType> roles = userroles.stream().map(RoleType::valueOf).collect(Collectors.toSet());
		userFileSystemService.editUser(username, roles);

		return "redirect:/users";
	}

	@PostMapping("/addUser")
	public String addUser(@RequestParam String username, @RequestParam String password, @RequestParam Set<String> userroles) {
		Set<RoleType> roles = userroles.stream().map(RoleType::valueOf).collect(Collectors.toSet());
		userFileSystemService.addUser(username, password, roles);

		return "redirect:/users";
	}
}