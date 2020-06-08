package eu.miroslavlehotsky.nas.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String deleteRecord(//
			@RequestParam String username, //
			RedirectAttributes redirectAttributes) {
		
		userFileSystemService.deleteUser(username);

		return "redirect:/users";
	}
}