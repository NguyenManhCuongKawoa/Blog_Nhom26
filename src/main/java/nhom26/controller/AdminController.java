package nhom26.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import nhom26.model.Post;
import nhom26.model.User;
import nhom26.repository.CommentRepository;
import nhom26.repository.PostRepository;
import nhom26.repository.RoleRepository;
import nhom26.repository.UserRepository;
import nhom26.util.PagerPost;
import nhom26.util.PagerUser;

@Controller
@RequestMapping("admin")
public class AdminController {
	
	@Autowired private UserRepository userRepository;
	@Autowired private RoleRepository roleRepository;
	@Autowired private PostRepository postRepository;
	@Autowired private CommentRepository commentRepository;
	

	@GetMapping("user")
	public String home(@RequestParam(defaultValue = "0") int page, Model model) {
		Page<User> users = userRepository.findAllByOrderByIdDesc(PageRequest.of(subtractPageByOne(page), 5));
		PagerUser pager = new PagerUser(users);

		model.addAttribute("pager", pager);
		
		return "/adminUser";
	}
	
	@GetMapping("/user/delete/{id}")
	public String deleteUser(@PathVariable Long id, Model model) {
		try {
			User user = userRepository.getById(id);
			List<Post> posts = postRepository.findByUser(user);
			
			for(Post post : posts) {
				commentRepository.deleteCommentByPostId(post.getId());
				postRepository.delete(post);
			}
			roleRepository.deleteRoleUserByUserId(id);
			commentRepository.deleteCommentByUserId(id);
			userRepository.deleteByUserId(id);
			Page<User> users = userRepository.findAllByOrderByIdDesc(PageRequest.of(subtractPageByOne(0), 5));
			
			PagerUser pager = new PagerUser(users);

			model.addAttribute("pager", pager);
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		
		return "/adminUser";
	}
	
	@GetMapping("/user/toggle/active/{id}")
	public String disableUser(@PathVariable Long id, Model model) {
		User user = userRepository.getById(id);
		user.setActive(user.getActive() == 1 ? 0 : 1);
		userRepository.save(user);
		Page<User> users = userRepository.findAllByOrderByIdDesc(PageRequest.of(subtractPageByOne(0), 5));
		
		PagerUser pager = new PagerUser(users);

		model.addAttribute("pager", pager);
		return "/adminUser";
	}
	
	@GetMapping("post")
	public String post(@RequestParam(defaultValue = "0") int page, Model model) {
		Page<Post> posts = postRepository.findAllByOrderByCreateDateDesc(PageRequest.of(subtractPageByOne(page), 5));
		PagerPost pager = new PagerPost(posts);

		model.addAttribute("pager", pager);
		
		return "/adminPost";
	}
	
	@GetMapping("/post/delete/{id}")
	public String deletePost(@PathVariable Long id, Model model) {
		postRepository.deleteById(id);
		Page<Post> posts = postRepository.findAllByOrderByCreateDateDesc(PageRequest.of(subtractPageByOne(0), 5));
		PagerPost pager = new PagerPost(posts);

		model.addAttribute("pager", pager);
		return "/adminPost";
	}
	

	
	
	@GetMapping("/post/accept/{id}")
	public String acceptPost(@PathVariable Long id, Model model) {
		Post post = postRepository.getById(id);
		post.setAccepted(1);
		postRepository.save(post);
		Page<Post> posts = postRepository.findAllByOrderByCreateDateDesc(PageRequest.of(subtractPageByOne(0), 5));
		PagerPost pager = new PagerPost(posts);

		model.addAttribute("pager", pager);
		
		return "/adminPost";
	}
	
	 private int subtractPageByOne(int page){
	        return (page < 1) ? 0 : page - 1;
	    }
}
