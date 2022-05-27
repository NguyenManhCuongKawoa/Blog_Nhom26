package nhom26.controller;

import java.security.Principal;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import nhom26.model.Post;
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
    public String getPostWithId(@PathVariable String username, Principal principal, Model model) {

        Optional<User> optionalUser = userService.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            model.addAttribute("user", user);
            if (principal != null && username.equals(principal.getName())) {
                model.addAttribute("current_user", principal.getName());
            }
            return "/user_info";
        }
        
        return "/error";
    }
    
    @GetMapping("/change_info/{username}")
    public String changeUserInfo(
    		@PathVariable String username, 
    		Principal principal, 
    		Model model
    ) {
    	
    	Optional<User> optionalUser = userService.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (principal != null && username.equals(principal.getName())) {
                model.addAttribute("user", user);
                return "/user_info_form";
            } else {
                return "/403";
            }
        }
        
        return "/error";
    }
    
    @PostMapping("/change_info")
    public String changeUserInfo(@Valid User user, BindingResult bindingResult) {
    	if (bindingResult.hasErrors()) {
            return "/user_info_form";
        } else {
            userService.update(user);
            return "redirect:/user/" + user.getUsername();
        }
    }
}
