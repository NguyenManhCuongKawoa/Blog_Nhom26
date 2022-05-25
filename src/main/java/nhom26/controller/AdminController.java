package nhom26.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import nhom26.model.Post;
import nhom26.model.User;
import nhom26.repository.PostRepository;
import nhom26.repository.UserRepository;

@Controller
@RequestMapping("admin")
public class AdminController {
	
	@Autowired private UserRepository userRepository;
	@Autowired private PostRepository postRepository;
	

	@GetMapping("home")
	public String home(Model model) {
		List<User> users = userRepository.findAll();
		List<Post> posts = postRepository.findAll();
		model.addAttribute("users", users);
		model.addAttribute("posts", posts);
		
		return "adminHome";
	}
}
