package nhom26.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import nhom26.model.User;
import nhom26.service.PostService;
import nhom26.service.UserService;

@Controller
public class UserController {
	
    private final UserService userService;

    @Autowired
    public UserController(PostService postService, UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{username}")
    public String getPostWithId(@PathVariable String username, Model model) {

        Optional<User> optionalUser = userService.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            model.addAttribute("user", user);
            return "/user_info";
        }
        
        return "/error";
    }
}
