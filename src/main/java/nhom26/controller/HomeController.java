package nhom26.controller;

import nhom26.model.Post;
import nhom26.service.PostService;
import nhom26.util.PagerPost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final PostService postService;

    @Autowired
    public HomeController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/home")
    public String home(@RequestParam(defaultValue = "0") int page,
                       Model model) {

        Page<Post> posts = postService.findAllOrderedByDatePageable(page);
        PagerPost pager = new PagerPost(posts);

        model.addAttribute("pager", pager);

        return "/home";
    }
    
    @GetMapping("/home/search")
    public String home(@RequestParam String keyword, Model model) {

        Page<Post> posts = postService.findAllByKeyWord(keyword);
        PagerPost pager = new PagerPost(posts);

        model.addAttribute("pager", pager);

        return "/home";
    }
    
    @GetMapping("/")
    public String home() {

        return "redirect:/home";
    }
}
