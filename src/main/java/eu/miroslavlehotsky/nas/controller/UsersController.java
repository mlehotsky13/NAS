package eu.miroslavlehotsky.nas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.miroslavlehotsky.nas.service.UserFileSystemService;

@Controller
@RequestMapping("/users")
public class UsersController {
	
	@Autowired
	private UserFileSystemService userFileSystemService;

	@GetMapping
	public String getUsersPage(Model model) {
		
		model.addAttribute("users", userFileSystemService.getAllUsers());
		
		return "users";
	}
}